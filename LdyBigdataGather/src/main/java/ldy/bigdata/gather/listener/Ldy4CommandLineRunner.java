package ldy.bigdata.gather.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class Ldy4CommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ldy-CommandLineRunner...run..." + Arrays.asList(args));
        System.out.println(Thread.currentThread().getName());
    }
}
