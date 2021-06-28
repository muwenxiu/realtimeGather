package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ColumnInfo {
    private String mysqlTableName;
    private String columnName;
    private String type;
    private String position;
}
