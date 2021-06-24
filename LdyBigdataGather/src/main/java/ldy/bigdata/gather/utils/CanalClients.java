package ldy.bigdata.gather.utils;

import ldy.bigdata.gather.config.CanalConfig;
import ldy.bigdata.gather.constants.SysInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(CanalConfig.class)
public class CanalClients {
    static final Logger log = LoggerFactory.getLogger(CanalClients.class);
    @Autowired
    private CanalConfig canalConfig;
    @Autowired
    private SysInit sysInit;
    public static Map<String, CanalClient> canalClients = new HashMap<>(10);
    public static boolean isInitOk = false;

    @PostConstruct
    public void init() {
        log.info("初始化canal连接...");
        for (String destination : canalConfig.getDestinations()) {
            log.info("初始化canal连接（" + destination + "）");
            String subscribe = canalSubscribeTable();
            CanalClient canalClient = new CanalClient(destination, canalConfig, subscribe);
            CanalClients.canalClients.put(destination, canalClient);
        }
        isInitOk = true;
    }

    private String canalSubscribeTable() {
        String suscribe = ".*\\..*";
//        Set<String> dataBases = sysInit.mysqlDatabase();
//        if (dataBases == null || dataBases.size() == 0) {
//            suscribe = ".*\\..*";
//        } else {
//            for (String dataBase : dataBases) {
//                suscribe = suscribe + dataBase + "\\..*,";
//            }
//            suscribe = suscribe.substring(0, suscribe.length() - 1);
//        }
        return suscribe;
    }


}
