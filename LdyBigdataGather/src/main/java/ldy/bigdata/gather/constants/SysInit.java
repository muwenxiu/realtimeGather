package ldy.bigdata.gather.constants;

import ldy.bigdata.gather.entities.KeyValue;
import ldy.bigdata.gather.entities.MysqlColumnInfo;
import ldy.bigdata.gather.entities.SingleData;
import ldy.bigdata.gather.mapper.sqlite.GatherDao;
//import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
//@Log4j2
public class SysInit {

    static final Logger log = LoggerFactory.getLogger(SysInit.class);
    /***
     * mysql和kudu表的对应关系
     * key:Mysql的表名
     * value:kudu表的表名
     */
    private Map<String, String> mysqlTable2KuduTable = new HashMap<>(80);
    /***
     * kudu表的分区字段信息
     * key:kudu的表名
     * value:kudu表的分区字段
     */
    private Map<String, String> kuduPartitionColumn = new HashMap<>(100);
    /**
     * mysql表字段信息
     * key:mysqlTableTame
     * key:columnName
     * value:columnType
     */
    private Map<String, Map<String, MysqlColumnInfo>> mysqlColumnInfo = new HashMap<>(80);
    /**
     * 要采集的mysql的库
     * key:要采集的mysql数据库名
     * value: 为null
     */
    private Map<String, String> gatherMysqlDatabase = new HashMap<>(20);
    @Autowired
    private GatherDao gatherDao;

    @PostConstruct
    public void init() {
        log.info("开始系统初始...");
        getTableNameOne2One();
        getGatherMysqlDatabase();
        getKuduPartitionColumn();
        getMysqlColumnInfo();
        log.info("开始系统初始完成。");
    }

    private void getTableNameOne2One() {
        log.info("初始化mysql表和kudu表对应关系...");
        try {
            List<KeyValue> lst = gatherDao.getTableNameMatchup();
            for (KeyValue kv : lst) {
                this.mysqlTable2KuduTable.put(kv.getKey(), kv.getValue());
            }
        } catch (Exception e) {
            log.error("初始化mysql表和kudu表对应关系失败，系统退出！");
            System.exit(-1);
        }
        log.info("初始化mysql表和kudu表对应关系完成。");
    }

    private void getGatherMysqlDatabase() {
        log.info("初始化mysql实例信息...");
        try {
            List<SingleData> lst = gatherDao.getGatherMysqlDatabase();
            for (SingleData v : lst) {
                this.gatherMysqlDatabase.put(v.getValue(), null);
            }
        } catch (Exception e) {
            log.error("初始化mysql实例信息信息失败，系统退出！");
            System.exit(-1);
        }
        log.info("初始化mysql实例信息完成。");
    }

    private void getKuduPartitionColumn() {
        log.info("初始化kudu表的分区字段信息...");
        try {
            List<KeyValue> lst = gatherDao.getKuduPartitionColumn();
            for (KeyValue kv : lst) {
                this.kuduPartitionColumn.put(kv.getKey(), kv.getValue());
            }
        } catch (Exception e) {
            log.error("初始化kudu表的分区字段信息失败，系统退出！");
            System.exit(-1);
        }
        log.info("初始化kudu表的分区字段信息完成。");
    }

    private void getMysqlColumnInfo() {
        log.info("初始化Mysql的字段信息...");
        try {
            List<MysqlColumnInfo> lstColumnInfo = gatherDao.getMysqlColumns();
            for (MysqlColumnInfo info : lstColumnInfo) {
                if (this.mysqlColumnInfo.containsKey(info.getTableName())) {
                    Map<String, MysqlColumnInfo> columnInfo = this.mysqlColumnInfo.get(info.getTableName());
                    if (columnInfo == null) {
                        columnInfo = new HashMap<>(60);
                    }
                    columnInfo.put(info.getColumnName(), info);
                    this.mysqlColumnInfo.put(info.getTableName(), columnInfo);
                } else {
                    Map<String, MysqlColumnInfo> columnInfo = new HashMap<>(60);
                    columnInfo.put(info.getColumnName().toLowerCase(), info);
                    this.mysqlColumnInfo.put(info.getTableName(), columnInfo);
                }
            }
        } catch (Exception e) {
            log.error("初始化Mysql的字段信息失败，系统退出！");
            System.exit(-1);
        }
        log.info("初始化Mysql的字段信息完成。");
    }

    public Set<String> mysqlDatabase() {
        return this.gatherMysqlDatabase.keySet();
    }

    public boolean mysqlTableExist(String mysqlTableName) {
        if (this.mysqlColumnInfo.containsKey(mysqlTableName.toLowerCase())) {
            return true;
        }
        return false;
    }

    public boolean columnExist(String mysqlTableName, String columnName) {
        if (this.mysqlColumnInfo.containsKey(mysqlTableName.toLowerCase())) {
            if (this.mysqlColumnInfo.get(mysqlTableName.toLowerCase()).containsKey(columnName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getMysqlColumnType(String tableName, String columnName) {
        if (this.mysqlColumnInfo.containsKey(tableName.toLowerCase())) {
            if (this.mysqlColumnInfo.get(tableName.toLowerCase()).containsKey(columnName.toLowerCase())) {
                return this.mysqlColumnInfo.get(tableName.toLowerCase()).get(columnName.toLowerCase()).getColumnType();
            }
        }
        return null;
    }

    public String getKuduTableName(String mysqlTableName) {
        if (this.mysqlTable2KuduTable.containsKey(mysqlTableName))
            return this.mysqlTable2KuduTable.get(mysqlTableName);
        else
            return null;
    }

    public String getKuduPartitionColumn(String kuduTableName) {
        if (this.kuduPartitionColumn.containsKey(kuduTableName))
            return this.kuduPartitionColumn.get(kuduTableName);
        else
            return null;
    }
}
