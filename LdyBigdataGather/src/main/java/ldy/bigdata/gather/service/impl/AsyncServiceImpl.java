package ldy.bigdata.gather.service.impl;

import ldy.bigdata.gather.service.AsyncService;
//import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
//@Log4j2
public class AsyncServiceImpl implements AsyncService {

    static final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");
        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");
        log.info("end executeAsync");
    }
}
