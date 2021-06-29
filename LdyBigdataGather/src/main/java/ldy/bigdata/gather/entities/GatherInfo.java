package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class GatherInfo {
    String taskId;
    String startGather;
    String endGather;
    String batchId;
    String taskStatus;
    String runStart;
    String runEnd;
    String updateTime;
    String description;
    int selectCnt;
    int insertCnt;
}
