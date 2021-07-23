package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TableDataCount {
    private String tableName;
    private int srcTableCount;
    private int tarTableCount;
    private boolean getCntError;
    private int diff;
    public int getDiff()
    {
        return srcTableCount-tarTableCount;
    }
}
