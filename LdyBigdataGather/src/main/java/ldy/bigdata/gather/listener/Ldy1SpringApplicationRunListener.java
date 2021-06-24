package ldy.bigdata.gather.listener;


import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class Ldy1SpringApplicationRunListener implements SpringApplicationRunListener {
    public Ldy1SpringApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("Ldy-SpringApplicationRunListener...starting...");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        Object o = environment.getSystemProperties().get("os.name");
        System.out.println("Ldy-SpringApplicationRunListener...environmentPrepared.." + o);
        System.out.println(Thread.currentThread().getName());
    }


    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("Ldy-SpringApplicationRunListener...contextPrepared...");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("Ldy-SpringApplicationRunListener...contextLoaded...");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void started(ConfigurableApplicationContext context) {

        System.out.println("Ldy-SpringApplicationRunListener...started...");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("Ldy-SpringApplicationRunListener...running...");
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("Ldy-SpringApplicationRunListener...failed...");
        System.out.println(Thread.currentThread().getName());
    }

}
