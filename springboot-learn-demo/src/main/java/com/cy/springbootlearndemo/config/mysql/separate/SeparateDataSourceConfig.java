package com.cy.springbootlearndemo.config.mysql.separate;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 读写分离数据源
 */

@Configuration
@MapperScan(basePackages = {"com.cy.springbootlearndemo.mapper"}, sqlSessionFactoryRef = "separateSqlSessionFactory")
public class SeparateDataSourceConfig {



    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="slave1DataSource")
    @ConfigurationProperties(prefix ="spring.datasource.salve1")
    public DataSource slave1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="myRoutingDataSource")
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
//        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }


    @Bean(name = "separateSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("myRoutingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        bean.setPlugins(new Interceptor[]{mybatisInterceptor});
        return bean.getObject();
    }


    @Bean(name = "separatePlatformTransactionManager")
    public DataSourceTransactionManager platformTransactionManager(@Qualifier("myRoutingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
