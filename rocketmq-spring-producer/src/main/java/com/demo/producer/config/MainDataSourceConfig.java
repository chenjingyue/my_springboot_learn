package com.demo.producer.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;


//@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"com.demo.producer.dao"}, sqlSessionFactoryRef =
        "mainSqlSessionFactory")
public class MainDataSourceConfig {

    @Resource
    private MybatisConfigurationSupport mybatisConfigurationSupport;

    @Bean(name = "mainDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.yg")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource datasource) throws Exception {
        return mybatisConfigurationSupport.build(datasource);
    }

}
