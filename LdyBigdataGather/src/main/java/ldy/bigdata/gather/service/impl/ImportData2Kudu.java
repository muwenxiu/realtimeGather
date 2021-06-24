package ldy.bigdata.gather.service.impl;


import com.alibaba.google.common.base.Strings;
import com.alibaba.otter.canal.protocol.CanalEntry;
import ldy.bigdata.gather.config.CanalConfig;
import ldy.bigdata.gather.constants.SysInit;
import ldy.bigdata.gather.entities.SqlValue;
import ldy.bigdata.gather.mapper.impala.CollectData;
import ldy.bigdata.gather.mapper.impala.CollectDataDealItem;
import ldy.bigdata.gather.mapper.impala.CollectDataQuoteItem;
import ldy.bigdata.gather.mapper.sqlite.GatherDao;
import ldy.bigdata.gather.service.IImportData;
//import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;

import java.util.ArrayList;
import java.util.List;

@Service("ImportData2Kudu")
//@Log4j2
public class ImportData2Kudu implements IImportData {
    static final Logger log = LoggerFactory.getLogger(ImportData2Kudu.class);
    @Autowired
    @Qualifier("impalaJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysInit sysInit;
    @Autowired
    private CanalConfig canalConfig;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private GatherDao gatherDao;
    @Autowired
    private CollectData collectData;
    @Autowired
    private CollectDataDealItem collectDataDealItem;
    @Autowired
    private CollectDataQuoteItem collectDataQuoteItem;

    @Override
    public boolean insert(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        UpdateData("插入", mysqlTableName, lstRowData);
        if (mysqlTableName.equals("purchase.purchase_project_item")) {
            Dwd_ItemCollect(mysqlTableName, lstRowData);
        } else if (mysqlTableName.equals("tender.bid_project_item")) {
            Dwd_ItemCollect(mysqlTableName, lstRowData);
        } else if (mysqlTableName.equals("auction.auction_project_item")) {
            Dwd_ItemCollect(mysqlTableName, lstRowData);
        } else if (mysqlTableName.equals("recruit.recruit")) {
            Dwd_ItemCollect(mysqlTableName, lstRowData);
        }
        return true;
    }

    @Override
    public boolean update(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        UpdateData("更新", mysqlTableName, lstRowData);
        return true;
    }

    @Override
    public boolean delete(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        return true;
    }

    private void UpdateData(String operType, String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        Ods_UpdateData(operType, mysqlTableName, lstRowData);
        Dwd_DataCollect_DealItem(mysqlTableName, lstRowData);
        Dwd_DataCollect_QuoteItem(mysqlTableName, lstRowData);
    }

    private static String sqlTmp = "upsert into ldydb_transaction.dwd_project_item\n" +
            "( project_id , project_type_name , item_name , patition_year , directory_id , company_id , create_time , update_time )\n" +
            "values\n";
    private static String sqlValue = "(%s,%s,%s,%s,%s,%s,%s,%s),";

    private boolean Dwd_ItemCollect(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        String project_id = "NULL", project_type_name = "NULL", item_name = "NULL";
        String patition_year = "NULL", directory_id = "NULL", company_id = "NULL";
        String create_time = "NULL", update_time = "NULL";
        String sql = "";
        if (mysqlTableName.equals("purchase.purchase_project_item")) {
            sql = sqlTmp;
            for (CanalEntry.RowData rowData : lstRowData) {
                project_type_name = "'purchase'";
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("project_id")) {
                        project_id = "'" + column.getValue() + "'";
                    }
                    if (columnName.equals("name")) {
                        item_name = "'" + column.getValue().replaceAll("[^\\u4e00-\\u9fa5]+", "") + "'";
                    }
                    if (columnName.equals("directory_id")) {
                        if (column.getValue().length() > 0) {
                            directory_id = column.getValue();
                        }
                    }
                    if (columnName.equals("company_id")) {
                        if (column.getValue().length() > 0) {
                            company_id = column.getValue();
                        }
                    }
                    if (columnName.equals("create_time")) {
                        create_time = "'" + column.getValue() + "'";
                        patition_year = column.getValue().substring(0, 4);
                    }
                    if (columnName.equals("update_time")) {
                        update_time = "'" + column.getValue() + "'";
                    }
                }
                String value = String.format(sqlValue, project_id, project_type_name, item_name, patition_year, directory_id, company_id, create_time, update_time);
                sql = sql + "\n" + value;
            }
            sql = sql.substring(0, sql.length() - 1);
            executeSql(sql);
            log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_item-purchase_project_item", lstRowData.size()));
            //向sqlite中记录采集日志信息
            gatherDao.updateRealtimeGather("project_item-purchase_project_item", lstRowData.size());
        } else if (mysqlTableName.equals("tender.bid_project_item")) {
            sql = sqlTmp;
            for (CanalEntry.RowData rowData : lstRowData) {
                project_type_name = "'bidding'";
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("project_id")) {
                        project_id = "'" + column.getValue() + "'";
                    }
                    if (columnName.equals("name")) {
                        item_name = "'" + column.getValue().replaceAll("[^\\u4e00-\\u9fa5]+", "") + "'";
                    }
                    if (columnName.equals("directory_id")) {
                        if (column.getValue().length() > 0) {
                            directory_id = column.getValue();
                        }
                    }
                    if (columnName.equals("company_id")) {
                        if (column.getValue().length() > 0) {
                            company_id = column.getValue();
                        }
                    }
                    if (columnName.equals("create_time")) {
                        create_time = "'" + column.getValue() + "'";
                        patition_year = column.getValue().substring(0, 4);
                    }
                    if (columnName.equals("update_time")) {
                        update_time = "'" + column.getValue() + "'";
                    }
                }
                String value = String.format(sqlValue, project_id, project_type_name, item_name, patition_year, directory_id, company_id, create_time, update_time);
                sql = sql + "\n" + value;
            }
            sql = sql.substring(0, sql.length() - 1);
            executeSql(sql);
            log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_item-bid_project_item", lstRowData.size()));
            //向sqlite中记录采集日志信息
            gatherDao.updateRealtimeGather("project_item-bid_project_item", lstRowData.size());
        } else if (mysqlTableName.equals("auction.auction_project_item")) {
            sql = sqlTmp;
            for (CanalEntry.RowData rowData : lstRowData) {
                project_type_name = "'auction'";
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("project_id")) {
                        project_id = "'" + column.getValue() + "'";
                    }
                    if (columnName.equals("name")) {
                        item_name = "'" + column.getValue().replaceAll("[^\\u4e00-\\u9fa5]+", "") + "'";
                    }
                    if (columnName.equals("directory_id")) {
                        if (column.getValue().length() > 0) {
                            directory_id = column.getValue();
                        }
                    }
                    if (columnName.equals("company_id")) {
                        if (column.getValue().length() > 0) {
                            company_id = column.getValue();
                        }
                    }
                    if (columnName.equals("create_time")) {
                        create_time = "'" + column.getValue() + "'";
                        patition_year = column.getValue().substring(0, 4);
                    }
                    if (columnName.equals("update_time")) {
                        update_time = "'" + column.getValue() + "'";
                    }
                }
                String value = String.format(sqlValue, project_id, project_type_name, item_name, patition_year, directory_id, company_id, create_time, update_time);
                sql = sql + "\n" + value;
            }
            sql = sql.substring(0, sql.length() - 1);
            executeSql(sql);
            log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_item-auction_project_item", lstRowData.size()));
            //向sqlite中记录采集日志信息
            gatherDao.updateRealtimeGather("project_item-auction_project_item", lstRowData.size());
        } else if (mysqlTableName.equals("recruit.recruit")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                String id = "";
                List<Long> catalogIDs = new ArrayList<>();
                boolean isInsertItem = false;
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        id = column.getValue();
                    }
                    if (columnName.equals("part_category")) {
                        if (column.getValue().length() > 0) {
                            String[] part_category = column.getValue().split(",");
                            for (int i = 0; i < part_category.length; i++) {
                                catalogIDs.add(Long.valueOf(part_category[i]));
                            }
                            isInsertItem = true;
                        } else {
                            isInsertItem = false;
                        }
                    }
                }
                if (isInsertItem) {
                    collectData.insertRecruitItem(id, catalogIDs);
                    log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_item-recruit", lstRowData.size()));
                    //向sqlite中记录采集日志信息
                    gatherDao.updateRealtimeGather("project_item-recruit", lstRowData.size());
                }
            }
        }
        return true;
    }

    private boolean Ods_UpdateData(String operType, String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        //获取要导入的kudu表
        int rowCnt = 0;
        String kuduTableName = sysInit.getKuduTableName(mysqlTableName);
        String partitionColumn = sysInit.getKuduPartitionColumn(kuduTableName);
        if (partitionColumn != null) {
            partitionColumn = partitionColumn.toLowerCase();
        }
        List<String> ids = new ArrayList();
        StringBuffer sqlBuf = new StringBuffer(1024);
        sqlBuf.append("upsert into ").append(kuduTableName).append("\n");
        String strColumn = getColumn(mysqlTableName, lstRowData.get(0).getAfterColumnsList(), partitionColumn);
        sqlBuf.append(strColumn);
        sqlBuf.append("values").append("\n");
        for (CanalEntry.RowData rowData : lstRowData) {
            rowCnt++;
            SqlValue sqlValue = getValues(mysqlTableName, rowData.getAfterColumnsList(), partitionColumn);
            sqlBuf.append("\n").append(sqlValue.getValue()).append(",");
            ids.add(sqlValue.getId());
        }
        sqlBuf.deleteCharAt(sqlBuf.length() - 1);
        sqlBuf.append(";");
        String sql = sqlBuf.toString();
        executeSql(sql);
        log.info(String.format("sql 执行成功。 表： %s,%s %d 条。%s", mysqlTableName, operType, rowCnt, ids));
        gatherDao.updateRealtimeGather(mysqlTableName, lstRowData.size());
        return true;
    }

    private void Dwd_DataCollect_DealItem(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        List<Long> ids = new ArrayList<>();
        if (mysqlTableName.equals("purchase.purchase_supplier_project_item_origin")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataDealItem.insertDealItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_deal_item-purchase", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_deal_item-purchase", ids.size());
            }
        } else if (mysqlTableName.equals("tender.bid_supplier_project_item_origin")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataDealItem.insertDealItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_deal_item-tender", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_deal_item-tender", ids.size());
            }
        } else if (mysqlTableName.equals("auction.auction_supplier_project_item_origin")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataDealItem.insertDealItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_deal_item-auction", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_deal_item-auction", ids.size());
            }
        } else if (mysqlTableName.equals("order.order_item_origin")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataDealItem.insertDealItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_deal_item-order", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_deal_item-order", ids.size());
            }
        } else if (mysqlTableName.equals("contract.contract_item_origin")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataDealItem.insertDealItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_deal_item-contract", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_deal_item-contract", ids.size());
            }
        }
    }

    private void Dwd_DataCollect_QuoteItem(String mysqlTableName, List<CanalEntry.RowData> lstRowData) {
        List<Long> ids = new ArrayList<>();
        if (mysqlTableName.equals("purchase.purchase_supplier_project_item_history")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataQuoteItem.insertQuoteItemPurchase(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_quote_item-purchase", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_quote_item-purchase", ids.size());
            }
        } else if (mysqlTableName.equals("tender.bid_supplier_project_item_history")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataQuoteItem.insertQuoteItemTender(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_quote_item-tender", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_quote_item-tender", ids.size());
            }
        } else if (mysqlTableName.equals("auction.auction_supplier_quote_recode")) {
            for (CanalEntry.RowData rowData : lstRowData) {
                for (Column column : rowData.getAfterColumnsList()) {
                    String columnName = column.getName().toLowerCase();
                    if (columnName.equals("id")) {
                        ids.add(Long.valueOf(column.getValue()));
                    }
                }
            }
            if (ids.size() > 0) {
                collectDataQuoteItem.insertQuoteItemAuction1(ids);
                collectDataQuoteItem.insertQuoteItemAuction2(ids);
                log.info(String.format("sql 执行成功。 表： %s,更新 %s 条。", "project_quote_item-auction", ids.size()));
                //向sqlite中记录采集日志信息
                gatherDao.updateRealtimeGather("project_quote_item-auction", ids.size());
                gatherDao.updateRealtimeGather("project_quote_item-auction1", ids.size());
                gatherDao.updateRealtimeGather("project_quote_item-auction2", ids.size());
            }
        }
    }

    private void executeSql(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            log.error("sql 执行失败！", e);
            log.error(sql);
        }
    }

    private String getColumn(String mysqlTableName, List<CanalEntry.Column> columns, String partitionColumn) {
        StringBuffer strColumnBuf = new StringBuffer(1024);
        strColumnBuf.append("(");
        for (Column column : columns) {
            if (sysInit.columnExist(mysqlTableName, column.getName().toLowerCase())) {
                if ("comment".equals(column.getName().toLowerCase()) || "describe".equals(column.getName().toLowerCase())) {
                    strColumnBuf.append(column.getName()).append("_ ,");
                } else {
                    strColumnBuf.append(column.getName()).append(",");
                }
            }
        }
        if (partitionColumn != null) {
            strColumnBuf.append("create_year").append(",");
        }
        String strColumn = strColumnBuf.toString();
        strColumn = strColumn.substring(0, strColumn.length() - 1) + ")\n";
        return strColumn;
    }

    private SqlValue getValues(String mysqlTableName, List<CanalEntry.Column> columns, String partitionColumn) {
        StringBuffer strValueBuf = new StringBuffer(1024);
        strValueBuf.append("(");
        String partitionData = null;
        String id = "";
        String warningInfo = null;
        for (Column column : columns) {
            String value = column.getValue();
            if (sysInit.columnExist(mysqlTableName, column.getName().toLowerCase())) {
                String columnType = sysInit.getMysqlColumnType(mysqlTableName, column.getName().toLowerCase());
                switch (columnType) {
                    case "varchar":
                    case "char":
                    case "datetime":
                    case "date":
                        if (Strings.isNullOrEmpty(value)) {
                            strValueBuf.append("NULL").append(",");
                        } else {
                            strValueBuf.append("'").append(value.replaceAll("[\\\\,']+?", "")).append("',");
                        }
                        break;
                    case "text":
                    case "mediumtext":
                        if (Strings.isNullOrEmpty(value)) {
                            strValueBuf.append("NULL").append(",");
                        } else {
                            String tem = value.replaceAll("[\\\\,']+?", "");
                            if (tem.getBytes().length > 65536) {
                                warningInfo = "表" + mysqlTableName + "的字段（" + column.getName() + "）值的长度为 " + tem.getBytes().length + "B，超过65536B";
                                tem = cutStr(tem, 65536);
                            }
                            strValueBuf.append("'").append(tem).append("',");
                        }
                        break;
                    case "tinyint":
                    case "smallint":
                    case "int":
                    case "bigint":
                    case "decimal":
                    default:
                        if (Strings.isNullOrEmpty(value)) {
                            strValueBuf.append("NULL").append(",");
                        } else {
                            strValueBuf.append(value).append(",");
                        }
                }
            }
            if (partitionColumn != null) {
                String[] partitionColumns = partitionColumn.split(",");
                for (int i = 0; i < partitionColumns.length; i++) {
                    if (partitionColumns[i].equals(column.getName().toLowerCase()) && value.length() >= 4) {
                        partitionData = value.substring(0, 4);
                    }
                }
            }
            if ("id".equals(column.getName().toLowerCase())) {
                id = column.getValue();
            }
        }
        if (mysqlTableName.equals("contract.contract_item_origin")) {
            int i = 1;
        }
        if (partitionColumn != null) {
            strValueBuf.append(partitionData).append(",");
        }
        String strValue = strValueBuf.toString();
        strValue = strValue.substring(0, strValue.length() - 1) + ")";
        if (warningInfo != null) {
            log.warn(warningInfo + " ID:" + id);
        }
        SqlValue result = new SqlValue();
        result.setValue(strValue);
        result.setId(id);
        return result;
    }

    public static String cutStr(String source, int lengthByte) {
        int sum = 0;
        String finalStr = "";
        if (null == source || source.getBytes().length <= lengthByte) {
            finalStr = (source == null ? "" : source);
        } else {
            for (int i = 0; i < source.length(); i++) {
                String str = source.substring(i, i + 1);
                // 累加单个字符字节数
                sum += str.getBytes().length;
                if (sum > lengthByte) {
                    finalStr = source.substring(0, i);
                    break;
                }
            }
        }
        return finalStr;
    }
}
