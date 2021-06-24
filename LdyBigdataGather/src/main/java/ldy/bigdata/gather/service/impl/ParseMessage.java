package ldy.bigdata.gather.service.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.protobuf.InvalidProtocolBufferException;
import ldy.bigdata.gather.constants.SysInit;
import ldy.bigdata.gather.service.IImportData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParseMessage {
    static final Logger log = LoggerFactory.getLogger(ParseMessage.class);
    @Autowired
    @Qualifier("ImportData2Kudu")
    private IImportData iImportData;
    @Autowired
    private SysInit sysInit;

    public void parser(List<CanalEntry.Entry> entryList) throws Exception {
        try {
            for (CanalEntry.Entry entry : entryList) {
                if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA) {
                    continue;
                }
                CanalEntry.RowChange rowChange = null;
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                boolean isDdl = rowChange.getIsDdl();
                if (isDdl) {
                    continue;
                }
                CanalEntry.EventType eventType = rowChange.getEventType();
                String tableName = entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName();
                //判断表是否采集
                if (!sysInit.mysqlTableExist(tableName.toLowerCase())) {
                    continue;
                }
                if (eventType == CanalEntry.EventType.DELETE) {
                    iImportData.delete(tableName, rowChange.getRowDatasList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    iImportData.insert(tableName, rowChange.getRowDatasList());
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    iImportData.update(tableName, rowChange.getRowDatasList());
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}