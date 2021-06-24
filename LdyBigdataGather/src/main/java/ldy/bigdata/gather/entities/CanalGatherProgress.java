package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class CanalGatherProgress {
    private String canalName;
    private String gatherLogName;
    private String gatherPosition;
    private String binlogName;
    private String binlogPosition;
}
