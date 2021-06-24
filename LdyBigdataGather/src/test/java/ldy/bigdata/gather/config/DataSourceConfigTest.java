package ldy.bigdata.gather.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class DataSourceConfigTest {

    @Test
    void sqliteDataSource() {
    }

    @Autowired
    private JdbcTemplate sqliteJdbcTemplate;

    @Test
    void sqliteJdbcTemplate() {
        List<Map<String, Object>> list = sqliteJdbcTemplate.queryForList("select * from AnalyseTask");
        System.out.println(list);
    }
}