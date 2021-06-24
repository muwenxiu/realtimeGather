package ldy.bigdata.gather.config;

import ldy.bigdata.gather.utils.LdyThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);
    @Value("${async.task.core_pool_size}")
    private int corePoolSize;
    @Value("${async.task.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.task.queue_capacity}")
    private int queueCapacity;
    @Value("${async.task.prefix}")
    private String namePrefix;

    @Bean(name = "asyncServiceExecutor")
    public Executor taskExecutor() {
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new LdyThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(namePrefix);
        executor.setKeepAliveSeconds(60);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Value("${async.job.core_pool_size}")
    private int jobCorePoolSize;
    @Value("${async.job.max_pool_size}")
    private int jobMaxPoolSize;
    @Value("${async.job.queue_capacity}")
    private int jobQueueCapacity;
    @Value("${async.job.prefix}")
    private String jobNamePrefix;

    @Bean(name = "jobExecutor")
    public ThreadPoolTaskExecutor jobExecutor() {
        logger.info("start jobExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(jobCorePoolSize);
        executor.setMaxPoolSize(jobMaxPoolSize);
        executor.setQueueCapacity(jobQueueCapacity);
        executor.setThreadNamePrefix(jobNamePrefix);
        executor.setKeepAliveSeconds(60);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
