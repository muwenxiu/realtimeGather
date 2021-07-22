package ldy.bigdata.gather.mapper.sqlite;

import ldy.bigdata.gather.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpsDao {
    List<KeyValue> gatherTables();

    List<KeyValue> gatherBatch(String status);

    boolean delGatherBatch(long batch);

    boolean delHistoryGatherTask(long batch);

    List<BatchInfo> gatherBatchInfo(long batch);

    List<ServiceInfo> getBackstageService();

    List<GatherInfo> gatherInfo(long batch);

    String getAnalyseBatchTime(String batch);

    List<KeyValue> getAnalyseBatch(String status);

    boolean delAnalyseTask(String time);

    List<AnalyseTaskInfo> getAnalyseTaskInfo(String batchId);

    List<MysqlColumnInfo> getMysqlColumnInfo(String mysqlTableName);

}
