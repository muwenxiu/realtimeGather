package ldy.bigdata.gather.mapper.sqlite;

import ldy.bigdata.gather.entities.DatabaseInfo;
import ldy.bigdata.gather.entities.KeyValue;
import ldy.bigdata.gather.entities.MysqlColumnInfo;
import ldy.bigdata.gather.entities.SingleData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface GatherDao {
    List<DatabaseInfo> getDatabaseInfo();

    List<KeyValue> getTableNameMatchup();

    List<KeyValue> getKuduPartitionColumn();

    List<MysqlColumnInfo> getMysqlColumns();

    List<SingleData> getGatherMysqlDatabase();

    boolean delMysqlColumnType(@Param("mysqlTableName") String mysqlTableName);

    boolean insertMysqlColumnTypeBatch(List<MysqlColumnInfo> lstMysqlColumnInfo);

    boolean updateRealtimeGather(@Param("mysqlTableName") String mysqlTableName,@Param("rowCnt") int rowCnt);
}
