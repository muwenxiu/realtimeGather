package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class DatabaseInfo {
    private String id;
    private String ip;
    private int port;
    private String user;
    private String pwd;
    private String dataBase;
    private String tableName;
}
