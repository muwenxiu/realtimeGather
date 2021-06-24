package ldy.bigdata.gather;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("ldy.bigdata.gather.mapper")
public class LdyBigdataGatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdyBigdataGatherApplication.class, args);
    }

}
