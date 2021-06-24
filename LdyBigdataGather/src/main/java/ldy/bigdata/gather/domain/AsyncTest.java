package ldy.bigdata.gather.domain;

//import lombok.extern.log4j.Log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executor;

@Configuration
//@Log4j2
public class AsyncTest {
    static final Logger log = LoggerFactory.getLogger(AsyncTest.class);
    @Autowired
    private ApplicationContext applicationContext;
    private long j = 0;

    //@Scheduled(fixedRate = 1000)
    //@Scheduled(fixedDelay = 3000)
    //@Scheduled(cron = "0/1 * * * * ?")
    //@Async("jobExecutor")
    public void test() {
        log.info("调度开始");
        try {
            log.info("测试运行启动" );
            Thread.sleep(50000);
            log.info("测试运行完成"  );
            //executeAsyncT(i);
        } catch (Exception e) {
        }
        log.info("调度结束");
    }


    //@Scheduled(fixedRate = 1000)
    //@Scheduled(fixedDelay = 1000)
    //@Scheduled(cron = "0/1 * * * * ?")
    //@Async("jobExecutor")
    public void test2() {
        log.info("调度开始");
        Executor executor = (Executor) applicationContext.getBean("asyncServiceExecutor");
        for (int i = 1; i < 4; i++) {
            j++;
            executor.execute(new Runnable() {
                long i;

                public Runnable init(long i) {
                    this.i = i;
                    return this;
                }

                @Override
                public void run() {
                    try {
                        log.info("测试运行启动" + i);
                        Thread.sleep(50000);
                        log.info("测试运行完成" + i);
                        //executeAsyncT(i);
                    } catch (Exception e) {
                    }
                }
            }.init(j));
            ;
        }
        log.info("调度结束");
    }

    @Async("asyncServiceExecutor")
    //通过测试，@Async不起作用
    public void executeAsyncT(int i) {

        try {
            log.info("测试运行启动aaaaaa" + i);
            Thread.sleep(2000);
            log.info("测试运行完成ssssss" + i);
        } catch (Exception e) {
        }
    }
}
