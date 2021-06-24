package ldy.bigdata.gather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

//@SpringBootTest(classes = Application.class)
@SpringBootTest
class LdyBigdataGatherApplicationTests {

    @Test
    void contextLoads() {
        String s = "aaa1,";
        s = s.substring(0, s.length() - 1);
        System.out.println(s);
    }

//    @Autowired
//    private JdbcTemplate jdbcTemplateImpala;

    @Test
    private void impalal() {
        System.out.println("aaaa");
//        List<Map<String, Object>> lst = jdbcTemplateImpala.queryForList("select * from default.my_partrition_test");
//        System.out.println(lst);
        System.out.println("ssssss");
    }
}
