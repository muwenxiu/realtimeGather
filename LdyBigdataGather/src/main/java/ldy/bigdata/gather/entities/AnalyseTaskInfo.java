package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AnalyseTaskInfo {
    String taskId;
    String batchId;
    String taskName;
    String taskStatus;
    String scriptStartTime;
    String scriptEndTime;
    String runStart;
    String runEnd;
    String gatherBatchId;
    String retryCnt;
}
