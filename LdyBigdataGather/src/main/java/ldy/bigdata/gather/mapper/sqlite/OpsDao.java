package ldy.bigdata.gather.mapper.sqlite;

import ldy.bigdata.gather.entities.KeyValue;
import ldy.bigdata.gather.entities.MysqlColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OpsDao {
    List<KeyValue> gatherTables();

    List<MysqlColumnInfo> getMysqlColumnInfo(String mysqlTableName);

}
