package ldy.bigdata.gather.listener;

import ldy.bigdata.gather.domain.CanalJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Ldy3ApplicationRunner implements ApplicationRunner {
    @Autowired
    private CanalJob canalJob;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Ldy-ApplicationRunner...run....");
        System.out.println(Thread.currentThread().getName());
        canalJob.runEx();
    }
}
