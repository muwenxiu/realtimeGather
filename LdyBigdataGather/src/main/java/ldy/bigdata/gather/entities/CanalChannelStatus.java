package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class CanalChannelStatus {
    private String canalChannelName;
    private boolean canalChannelStatus;
}
