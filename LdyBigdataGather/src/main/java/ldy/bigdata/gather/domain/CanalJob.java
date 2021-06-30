package ldy.bigdata.gather.domain;

import com.alibaba.otter.canal.protocol.Message;
import ldy.bigdata.gather.service.impl.ParseMessage;
import ldy.bigdata.gather.utils.CanalClient;
import ldy.bigdata.gather.utils.CanalClients;
//import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.Executor;


@Configuration
@ConditionalOnClass(CanalClients.class)
//@Log4j2
public class CanalJob {

    static final Logger log = LoggerFactory.getLogger(CanalJob.class);
    @Autowired
    private ParseMessage parseMessage;
    @Autowired
    private ApplicationContext applicationContext;

    //@Async("jobExecutor")
    //@Scheduled(cron = "0/5 * * * * ?")
    //@Scheduled(fixedRate = 2000)
    public void job() {
        //log.info("定时任务启动");
        //this.run();
    }

    private void run() {
        log.info("采集任务启动");
        log.info("开始同步 " + CanalClients.canalClients.size() + " 个库的数据...");
        Executor executor = (Executor) applicationContext.getBean("asyncServiceExecutor");
        for (Map.Entry<String, CanalClient> entry : CanalClients.canalClients.entrySet()) {
            executor.execute(() -> {
                log.info("开始采集 (" + entry.getKey() + ")");
                CanalClient canalClient = entry.getValue();
                this.getMessage(canalClient);
            });
        }
    }

    @Value("${ldy.threadSleep}")
    private long threadSleep = 1000;
    @Value("${ldy.isSleep}")
    private boolean isSleep = true;

    public void runEx() {
        runGatherData();
    }

    private void runGatherData() {
        log.info("采集任务启动");
        log.info("开始同步 " + CanalClients.canalClients.size() + " 个库的数据...");
        Executor executor = (Executor) applicationContext.getBean("asyncServiceExecutor");
        for (Map.Entry<String, CanalClient> entry : CanalClients.canalClients.entrySet()) {
            executor.execute(() -> {
                log.info("开始采集 (" + entry.getKey() + ")");
                while (true) {
                    CanalClient canalClient = entry.getValue();
                    this.getMessage(canalClient);
                    if (isSleep) {
                        try {
                            Thread.sleep(threadSleep);
                        } catch (Exception e) {
                        }
                    }
                }
            });
        }
    }

    private void getMessage(CanalClient canalClient) {
        try {
            Message message = canalClient.getConnect().getWithoutAck(100);
            long batchID = message.getId();
            int size = message.getEntries().size();
            if (batchID < 0 || size == 0) {
                return;
            }
            try {
                parseMessage.parser(message.getEntries());
                canalClient.getConnect().ack(batchID);
            } catch (Exception e) {
                log.error("解析错误！", e);
                canalClient.getConnect().rollback();
            }
        } catch (Exception e) {
            log.error("canal连接 " + canalClient.name() + " 估计挂了", e);
            try {
                Thread.sleep(10 * 1000);
            } catch (Exception ee) {
            }
            canalClient.disconnect();
            canalClient.getConnect();
        }
    }
}