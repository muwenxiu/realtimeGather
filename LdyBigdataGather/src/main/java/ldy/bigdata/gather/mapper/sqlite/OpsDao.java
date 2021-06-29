package ldy.bigdata.gather.mapper.sqlite;

import ldy.bigdata.gather.entities.BatchInfo;
import ldy.bigdata.gather.entities.GatherInfo;
import ldy.bigdata.gather.entities.KeyValue;
import ldy.bigdata.gather.entities.MysqlColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpsDao {
    List<KeyValue> gatherTables();

    List<KeyValue> gatherBatch();

    List<BatchInfo> gatherBatchInfo(long batch);

    List<GatherInfo> gatherInfo(long batch);

    List<MysqlColumnInfo> getMysqlColumnInfo(String mysqlTableName);

}
