package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class BatchInfo {
    String gatherBatchID;
    String gatherStatus;
    String analyseStatus;
    String impalaToMysqlStatus;
    String analyseStatusUpdateTime;
    String createTime;
    String updateTime;
}
