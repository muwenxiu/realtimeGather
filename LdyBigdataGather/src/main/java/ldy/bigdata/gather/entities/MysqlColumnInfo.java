package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class MysqlColumnInfo {
    private String tableName;
    private String columnName;
    private String columnType;
    private int position;
}
