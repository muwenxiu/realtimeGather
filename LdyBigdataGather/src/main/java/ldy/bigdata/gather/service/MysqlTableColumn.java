package ldy.bigdata.gather.service;

import ldy.bigdata.gather.entities.MysqlColumnInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MysqlTableColumn {
    List<MysqlColumnInfo> getMysqlColumnInfo(String databaseName, String tableNames);
 }
