package ldy.bigdata.gather.mapper.uresacl;

import ldy.bigdata.gather.entities.MysqlColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sysConfigUresacl")
@Mapper
public interface SysConfig {
    List<MysqlColumnInfo> getMysqlColumnInfo(@Param("databaseName") String databaseName, @Param("tableNames")String tableNames);
}
