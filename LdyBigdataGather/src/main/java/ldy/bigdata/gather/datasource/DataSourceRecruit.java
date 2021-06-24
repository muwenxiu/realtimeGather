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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "ldy.bigdata.gather.mapper.recruit",sqlSessionFactoryRef = "recruitSqlSessionFactory")
public class DataSourceRecruit {
    @Bean(name = "recruitDataSource")
    @Qualifier(value = "recruitDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.recruit")
    public DataSource recruitDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    private String mybatisConfigLocation = "classpath:mybatis/mybatis.cfg.xml";
    private String mybatisLocations = "classpath:mybatis/mapper/recruit/*.xml";

    @Bean(name = "recruitSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("recruitDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatisConfigLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisLocations));
        return bean.getObject();
    }

    @Bean(name = "recruitSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("recruitSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }


}
