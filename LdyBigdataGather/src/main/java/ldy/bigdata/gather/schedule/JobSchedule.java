package ldy.bigdata.gather.schedule;

import ldy.bigdata.gather.utils.CanalClient;
import ldy.bigdata.gather.utils.CanalClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

@Configuration
public class JobSchedule {

    static final Logger log = LoggerFactory.getLogger(JobSchedule.class);
    @Autowired
    private ApplicationContext applicationContext;

    //canal连接检测
    //@Scheduled(cron = "0/10 * * * * ?")
    public void canalHeartbeatMonitor() {
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) applicationContext.getBean("asyncServiceExecutor");
        log.info("TaskCount:{},CompletedTaskCount:{} ,ActiveCount:{},QueueSize:{}",
                executor.getMaxPoolSize(),
                executor.getCorePoolSize(),
                executor.getActiveCount(),
                executor.getPoolSize());
        for (Map.Entry<String, CanalClient> entry : CanalClients.canalClients.entrySet()) {
            CanalClient canalClient = entry.getValue();
            log.info(String.format("canal（%-30s）连接状态：checkValid=%s", entry.getKey(), canalClient.getConnect().checkValid()));
        }
    }


}
