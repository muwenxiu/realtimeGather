package ldy.bigdata.gather.service;

import com.google.common.base.Strings;
import ldy.bigdata.gather.entities.DatabaseInfoExtend;
import ldy.bigdata.gather.entities.TableDataCount;
import ldy.bigdata.gather.mapper.sqlite.OpsDao;
import ldy.bigdata.gather.utils.DBClient;
import ldy.bigdata.gather.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataVerify {
    static final Logger log = LoggerFactory.getLogger(DataVerify.class);
    @Autowired
    private OpsDao opsDao;
    @Autowired
    @Qualifier("impalaJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<TableDataCount> getOdsDataVerify() {
        List<TableDataCount> result = new ArrayList<>();
        List<DatabaseInfoExtend> lstTableInfo = opsDao.getGatherTableInfo();
        long start = System.nanoTime();
        result = lstTableInfo.parallelStream().map(
                (tableInfo) -> {
                    TableDataCount tdc = new TableDataCount();
                    tdc.setTableName(tableInfo.getTableName());
                    tdc.setGetCntError(false);
                    try {
                        DBClient dbClient = new DBClient(tableInfo.getIp(), tableInfo.getPort(), tableInfo.getUser(), tableInfo.getPwd(), tableInfo.getDataBase());
                        String sql;
                        if (Strings.isNullOrEmpty(tableInfo.getPartitionColumn())) {
                            sql = "select count(1) as cnt from " + tableInfo.getTableName();
                        } else {
                            sql = "select count(1) as cnt from " + tableInfo.getTableName() + " where " + tableInfo.getPartitionColumn() + " <= CURRENT_DATE()";
                        }
                        log.info(sql);
                        Map<String, String> cntMap = dbClient.executeOne(sql);
                        tdc.setSrcTableCount(Integer.valueOf(cntMap.get("cnt")));
                        //////////////////////////////////////////////////////////
                        if (Strings.isNullOrEmpty(tableInfo.getPartitionColumn())) {
                            sql = "select count(1) as cnt from " + tableInfo.getKuduTableName();
                        } else {
                            sql = "select count(1) as cnt from " + tableInfo.getKuduTableName() + " where " + tableInfo.getPartitionColumn() + " <= trunc(current_timestamp(),'dd')";
                        }
                        log.info(sql);
                        Map<String, Object> kuduCnt = jdbcTemplate.queryForMap(sql);
                        Object cnt = kuduCnt.get("cnt");
                        tdc.setTarTableCount(Integer.valueOf(cnt.toString()));
                    } catch (Exception e) {
                        tdc.setGetCntError(true);
                    }
                    return tdc;
                }
        ).collect(Collectors.toList());
        System.out.println(System.nanoTime() - start);
        return result;
    }

    public List<TableDataCount> getDataQualityWarning_Month(String mysqlTableName) {
        String dataBase = mysqlTableName.split("\\.")[0];
        String tableName = mysqlTableName.split("\\.")[1];
        DatabaseInfoExtend tableInfo = opsDao.getGatherTableInfoByTableName(dataBase, tableName);
        DBClient dbClient = new DBClient(tableInfo.getIp(), tableInfo.getPort(), tableInfo.getUser(), tableInfo.getPwd(), tableInfo.getDataBase());
        String sql;
        //获取Mysql中表的月度数据量统计
        sql = "SELECT \n" +
                " date_format(create_time,'%Y-%m') as dataDate ,\n" +
                " count(1) as dataCnt\n" +
                " from " + mysqlTableName + "\n" +
                " GROUP BY date_format(create_time,'%Y-%m')\n" +
                " order BY date_format(create_time,'%Y-%m') \n";
        List<Map<String, String>> lstMysqlDataCnt = dbClient.executeMore(sql);
        //获取impala中表的月度数据量统计
        sql = "SELECT \n" +
                " from_timestamp(trunc(create_time,'MM'),'yyyy-MM') as dataDate ,\n" +
                " count(1) as dataCnt\n" +
                " from " + tableInfo.getKuduTableName() + "\n" +
                " GROUP BY trunc(create_time,'MM')\n" +
                " order BY trunc(create_time,'MM')";
        List<Map<String, Object>> lstKuduCnt = jdbcTemplate.queryForList(sql);

        List<TableDataCount> result = new ArrayList<>();
        for (Map<String, String> mysqlDate : lstMysqlDataCnt) {
            TableDataCount tdc = new TableDataCount();
            tdc.setDataDate(mysqlDate.get("dataDate"));
            tdc.setSrcTableCount(Integer.valueOf(mysqlDate.get("dataCnt")));
            for (Map<String, Object> kuduCnt : lstKuduCnt) {
                if (mysqlDate.get("dataDate").equals(kuduCnt.get("dataDate"))) {
                    tdc.setTarTableCount(Integer.valueOf(kuduCnt.get("dataCnt").toString()));
                }
            }
            result.add(tdc);
        }
        return result;
    }

    public List<TableDataCount> getDataQualityWarning_Day(String mysqlTableName, String dataDate) {
        String date = dataDate + "-01";
        String dataBase = mysqlTableName.split("\\.")[0];
        String tableName = mysqlTableName.split("\\.")[1];
        DatabaseInfoExtend tableInfo = opsDao.getGatherTableInfoByTableName(dataBase, tableName);
        DBClient dbClient = new DBClient(tableInfo.getIp(), tableInfo.getPort(), tableInfo.getUser(), tableInfo.getPwd(), tableInfo.getDataBase());
        String sql;
        String where = "";
        //获取Mysql中表的月度数据量统计
        if (!Strings.isNullOrEmpty(tableInfo.getPartitionColumn())) {
            where = " where " + tableInfo.getPartitionColumn() + " >= str_to_date('" + date + "','%Y-%m-%d') \n" +
                    " and " + tableInfo.getPartitionColumn() + "< date_add( str_to_date('" + date + "','%Y-%m-%d') ,interval 1 month)";
        }
        sql = "SELECT \n" +
                " date_format(create_time,'%Y-%m-%d') as dataDate ,\n" +
                " count(1) as dataCnt\n" +
                " from " + mysqlTableName + "\n" +
                where + " \n" +
                " GROUP BY date_format(create_time,'%Y-%m-%d')\n" +
                " order BY date_format(create_time,'%Y-%m-%d') \n";
        List<Map<String, String>> lstMysqlDataCnt = dbClient.executeMore(sql);
        //获取impala中表的月度数据量统计
        if (!Strings.isNullOrEmpty(tableInfo.getPartitionColumn())) {
            where = " where " + tableInfo.getPartitionColumn() + " >= to_timestamp('" + date + "','yyyy-MM-dd') \n" +
                    " and " + tableInfo.getPartitionColumn() + "< date_add(to_timestamp('" + date + "','yyyy-MM-dd'),INTERVAL 1 MONTHS)";
        }
        sql = "SELECT \n" +
                " from_timestamp(trunc(create_time,'dd'),'yyyy-MM-dd')  as dataDate,\n" +
                " count(1) as dataCnt\n" +
                " from " + tableInfo.getKuduTableName() + "\n" +
                where + " \n" +
                " GROUP BY trunc(create_time,'dd')\n" +
                " order BY trunc(create_time,'dd')";
        List<Map<String, Object>> lstKuduCnt = jdbcTemplate.queryForList(sql);
        List<TableDataCount> result = new ArrayList<>();
        for (Map<String, String> mysqlDate : lstMysqlDataCnt) {
            TableDataCount tdc = new TableDataCount();
            tdc.setDataDate(mysqlDate.get("dataDate"));
            tdc.setSrcTableCount(Integer.valueOf(mysqlDate.get("dataCnt")));
            for (Map<String, Object> kuduCnt : lstKuduCnt) {
                if (mysqlDate.get("dataDate").equals(kuduCnt.get("dataDate"))) {
                    tdc.setTarTableCount(Integer.valueOf(kuduCnt.get("dataCnt").toString()));
                }
            }
            result.add(tdc);
        }
        return result;
    }


    /***
     * 重新采集：按照月重新采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    public boolean reGatherData_month(String mysqlTableName, String dataDate) {
        return true;
    }

    public String reGatherData_month_deleteSql(String mysqlTableName, String dataDate) {
        String date = dataDate + "-01";
        String dataBase = mysqlTableName.split("\\.")[0];
        String tableName = mysqlTableName.split("\\.")[1];
        DatabaseInfoExtend tableInfo = opsDao.getGatherTableInfoByTableName(dataBase, tableName);
        String sql;
        String where = "";
        if (!Strings.isNullOrEmpty(tableInfo.getPartitionColumn())) {
            where = " where " + tableInfo.getPartitionColumn() + " >= to_timestamp('" + date + "','yyyy-MM-dd') \n" +
                    " and " + tableInfo.getPartitionColumn() + "< date_add(to_timestamp('" + date + "','yyyy-MM-dd'),INTERVAL 1 MONTHS)";
        }
        sql = "DELETE from " + tableInfo.getKuduTableName() + "\n" +
                where + " \n";
        return sql;
    }

    public String reGatherData_month_insertSql(String mysqlTableName, String dataDate) {
        String date = dataDate + "-01 00:00:00";
        Date startMonth = DateUtils.getDate(date, "yyyy-MM-dd HH:mm:ss");
        Date endMonth = DateUtils.getLastDayOfMonth(startMonth);
        String strEndMonth = DateUtils.dateFormat(endMonth, "yyyy-MM-dd HH:mm:ss");
        String dataBase = mysqlTableName.split("\\.")[0];
        String tableName = mysqlTableName.split("\\.")[1];
        DatabaseInfoExtend tableInfo = opsDao.getGatherTableInfoByTableName(dataBase, tableName);
        String sql;
        sql = "INSERT INTO DataGatherTask (task_id, startGather, endGather, batchId, taskStatus, runStart, runEnd, update_time, description, selectCnt, insertCnt) \n" +
                " VALUES \n" +
                "(" + tableInfo.getId() + ",'" + date + "', '" + strEndMonth + "', 1, 'INITIALIZED', null, null, null, NULL, 0, 0)";
        return sql;
    }

    public String getGatherProgress(String mysqlTableName, String dataDate) {
        String date = dataDate + "-01 00:00:00";
        Date startMonth = DateUtils.getDate(date, "yyyy-MM-dd HH:mm:ss");
        Date endMonth = DateUtils.getLastDayOfMonth(startMonth);
        String strEndMonth = DateUtils.dateFormat(endMonth, "yyyy-MM-dd HH:mm:ss");
        String dataBase = mysqlTableName.split("\\.")[0];
        String tableName = mysqlTableName.split("\\.")[1];
        DatabaseInfoExtend tableInfo = opsDao.getGatherTableInfoByTableName(dataBase, tableName);
        return opsDao.getGatherProgress(Integer.valueOf(tableInfo.getId()) , date,strEndMonth);
    }

    /***
     * 重新采集：按照月叠加采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    public boolean recoverGather_month(String mysqlTableName, String dataDate) {
        return true;
    }

    /***
     * 重新采集：按照日重新采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    public boolean reGatherData_day(String mysqlTableName, String dataDate) {
        return true;
    }

    /***
     * 重新采集：按照日叠加采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    public boolean recoverGather_day(String mysqlTableName, String dataDate) {
        return true;
    }


}
