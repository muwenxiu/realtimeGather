package ldy.bigdata.gather.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class Ldy2ApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("Ldy-ApplicationContextInitializer...initialize..." + configurableApplicationContext);
        System.out.println(Thread.currentThread().getName());
    }
}
