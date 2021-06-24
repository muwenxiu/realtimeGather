package ldy.bigdata.gather.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "ldy.bigdata.gather.mapper.impala", sqlSessionFactoryRef = "impalaSqlSessionFactory")
public class DataSourceImpala {
    private String mybatisConfigLocation = "classpath:mybatis/mybatis.cfg.xml";
    private String mybatisLocations = "classpath:mybatis/mapper/impala/*.xml";

    @Primary
    @Bean(name = "impalaDataSource")
    @Qualifier(value = "impalaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.impala")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "impalaSqlSessionFactory")
    @Primary
    public SqlSessionFactory getSessionFactory(@Qualifier("impalaDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatisConfigLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisLocations));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "impalaSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("impalaSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
    @Primary
    @Bean(name = "impalaJdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("impalaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
