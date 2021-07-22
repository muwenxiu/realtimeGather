package ldy.bigdata.gather.service;

import ldy.bigdata.gather.entities.ServiceInfo;
import ldy.bigdata.gather.service.impl.ImportData2Kudu;
import ldy.bigdata.gather.utils.ExecComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BackstageService {
    static final Logger log = LoggerFactory.getLogger(BackstageService.class);

    public static void getBackstageServiceStatus(List<ServiceInfo> lstServiceInfo) {
        lstServiceInfo.stream().map(
                (data) -> {
                    List<String> lstLine = ExecComment.exec(new String[]{"/bin/sh", "-c", "ps -ef | grep " + data.getBackstageServiceName()});
                    log.info("linux 命令返回的行数：" + lstLine.size());
                    if (lstLine.size() > 1) {
                        data.setBackstageServiceStatus("OK");
                    } else {
                        data.setBackstageServiceStatus("ERROR");
                    }
                    return null;
                }
        ).collect(Collectors.toList());

    }

    public static boolean RestartBackstageServiceStatus(String serviceName, String serviceStartScript) {
        ExecComment.exec(new String[]{"/bin/sh", "-c", "sh   " + serviceStartScript});
        List<String> lstLine = ExecComment.exec(new String[]{"/bin/sh", "-c", "ps -ef | grep " + serviceName});
        if (lstLine.size() > 1 && lstLine.size() <= 2) {
            return true;
        } else {
            return false;
        }
    }
}
