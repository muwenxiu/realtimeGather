package ldy.bigdata.gather.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ServiceInfo {
    private String backstageServiceName;
    private String backstageServiceStatus;
}