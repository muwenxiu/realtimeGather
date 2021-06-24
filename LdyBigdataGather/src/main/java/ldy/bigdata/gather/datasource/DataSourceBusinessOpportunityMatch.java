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

@MapperScan(basePackages = "ldy.bigdata.gather.mapper.businessopportunitymatch",sqlSessionFactoryRef = "businessOpportunityMatchSqlSessionFactory")
public class DataSourceBusinessOpportunityMatch {

    @Bean(name = "businessOpportunityMatchDataSource")
    @Qualifier(value = "businessOpportunityMatchDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.business-opportunity-match")
    public DataSource business_opportunity_matchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    private String mybatisConfigLocation = "classpath:mybatis/mybatis.cfg.xml";
    private String mybatisLocations = "classpath:mybatis/mapper/businessopportunitymatch/*.xml";

    @Bean(name = "businessOpportunityMatchSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("businessOpportunityMatchDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatisConfigLocation));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisLocations));
        return bean.getObject();
    }

    @Bean(name = "businessOpportunityMatchSqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("businessOpportunityMatchSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }


}
