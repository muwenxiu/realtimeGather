package ldy.bigdata.gather.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ldy.bigdata.gather.entities.*;
import ldy.bigdata.gather.mapper.sqlite.GatherDao;
import ldy.bigdata.gather.service.InitConfig;
import ldy.bigdata.gather.utils.CanalClient;
import ldy.bigdata.gather.utils.CanalClients;
import ldy.bigdata.gather.utils.DBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

@Service
public class InitConfigImpl implements InitConfig {
    @Autowired
    private GatherDao gatherDao;
    @Value("${canal.server.confPath}")
    private String canalServerConfigPath;

    @Override
    public List<SingleData> getMysqlDatabase() {
        List<SingleData> lst = gatherDao.getGatherMysqlDatabase();
        return lst;
    }

    @Override
    public List<CanalChannelStatus> getCanalChannel() {
        List<CanalChannelStatus> lst = new ArrayList<>();
        for (Map.Entry<String, CanalClient> entry : CanalClients.canalClients.entrySet()) {
            CanalClient canalClient = entry.getValue();
            CanalChannelStatus canalChannelStatus = new CanalChannelStatus();
            canalChannelStatus.setCanalChannelName(canalClient.name());
            canalChannelStatus.setCanalChannelStatus(canalClient.getConnect().checkValid());
            lst.add(canalChannelStatus);
        }
        return lst;
    }

    @Override
    public List<CanalGatherProgress> getCanalGatherProgress() {
        List<CanalGatherProgress> result = new ArrayList<>();
        Path canalConfigPath = Paths.get(canalServerConfigPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(canalConfigPath)) {
            for (Path confPath : stream) {
                if (!Files.isDirectory(confPath)) {
                    continue;
                }
                boolean isExistProp = false;
                for (Path p : Files.newDirectoryStream(confPath)) {
                    if (p.getFileName().toString().equals("instance.properties")) {
                        isExistProp = true;
                        break;
                    }
                }
                if (isExistProp == false) {
                    continue;
                }
                CanalGatherProgress canalGatherProgress = new CanalGatherProgress();
                for (Path file : Files.newDirectoryStream(confPath)) {
                    if (!"meta.dat".equals(file.getFileName().toString()) && !"instance.properties".equals(file.getFileName().toString())) {
                        continue;
                    }
                    if ("meta.dat".equals(file.getFileName().toString())) {
                        String destination = confPath.getFileName().toString();
                        BufferedReader bufferedReader = Files.newBufferedReader(file);
                        String line = bufferedReader.readLine();
                        JSONObject jsonObject = JSONObject.parseObject(line);
                        JSONArray clientDatas = jsonObject.getJSONArray("clientDatas");
                        JSONObject jsonObject1 = clientDatas.getJSONObject(0).getJSONObject("cursor").getJSONObject("postion");
                        String journalName = jsonObject1.getString("journalName");
                        String position = jsonObject1.getString("position");
                        canalGatherProgress.setCanalName(destination);
                        canalGatherProgress.setGatherLogName(journalName);
                        canalGatherProgress.setGatherPosition(position);
                    }
                    if ("instance.properties".equals(file.getFileName().toString())) {
                        Map<String, String> mysqlBinlogMaster = getMysqlBinlogMaster(file);
                        canalGatherProgress.setBinlogName(mysqlBinlogMaster.get("File"));
                        canalGatherProgress.setBinlogPosition(mysqlBinlogMaster.get("Position"));
                    }
                }
                result.add(canalGatherProgress);
            }
        } catch (IOException e) {
        }
        return result;
    }

    @Override
    public boolean initMysqlColumn() {
        List<DatabaseInfo> lstDatabaseInfo = gatherDao.getDatabaseInfo();
        for (DatabaseInfo databaseInfo : lstDatabaseInfo) {
            DBClient dbClient = new DBClient(databaseInfo.getIp(), databaseInfo.getPort(), databaseInfo.getUser(), databaseInfo.getPwd(), databaseInfo.getDataBase());
            String sql = " select\n" +
                    "        lower(concat(TABLE_SCHEMA,'.',table_name) ) as  tableName,\n" +
                    "        lower(COLUMN_NAME) as columnName,\n" +
                    "        lower(DATA_TYPE)   as columnType,\n" +
                    "        ORDINAL_POSITION   as position\n" +
                    "        from information_schema.columns b\n" +
                    "        where  lower(b.TABLE_SCHEMA)= '" + databaseInfo.getDataBase() + "'\n" +
                    "         and lower(b.table_name) in ( '" + databaseInfo.getTableName() + "' )\n" +
                    "        order by b.ORDINAL_POSITION";
            List<MysqlColumnInfo> lstMysqlColumnInfo = dbClient.execute(sql, MysqlColumnInfo.class);
            String mysqlTableName = databaseInfo.getDataBase().toLowerCase() + "." + databaseInfo.getTableName().toLowerCase();
            Object object = gatherDao.delMysqlColumnType(mysqlTableName);
            object = gatherDao.insertMysqlColumnTypeBatch(lstMysqlColumnInfo);
        }
        return true;
    }


    private Map<String, String> getMysqlBinlogMaster(Path file) {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(file));
            String host = properties.getProperty("canal.instance.master.address").split(":")[0];
            int port = Integer.valueOf(properties.getProperty("canal.instance.master.address").split(":")[1]);
            String user = properties.getProperty("canal.instance.dbUsername");
            String password = properties.getProperty("canal.instance.dbPassword");
            DBClient dbClient = new DBClient(host, port, user, password, null);
            List<Map<String, String>> contents = dbClient.execute("show master status");
            return contents.get(0);
        } catch (Exception e) {
        }
        return null;
    }
}
