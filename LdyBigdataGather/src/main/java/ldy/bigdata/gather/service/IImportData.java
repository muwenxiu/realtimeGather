package ldy.bigdata.gather.service;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

public interface IImportData {
    /**
     * @param mysqlTableName
     * @param lstRowData
     * @return
     */
    boolean insert(String mysqlTableName, List<CanalEntry.RowData> lstRowData);

    /**
     * 删除数据
     *
     * @param mysqlTableName
     * @param lstRowData
     * @return
     */
    boolean delete(String mysqlTableName, List<CanalEntry.RowData> lstRowData);

    /**
     * 更新数据
     *
     * @param mysqlTableName
     * @param lstRowData
     * @return
     */
    boolean update(String mysqlTableName, List<CanalEntry.RowData> lstRowData);
}
