package com.example.my_springboot_learn.base.mysql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@MapperScan(annotationClass = Mapper.class, basePackages = {"com.example.my_springboot_learn.mapper"}, sqlSessionFactoryRef =
        "mainSqlSessionFactory")
public class MainDataSourceConfig {

    @Resource
    private MybatisConfigurationSupport mybatisConfigurationSupport;

    @Primary
    @Bean(name = "mainDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.cy")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mainDataSource")
    @Primary
    public DataSource mainDataSource() {
        DataSource dataSource = dataSourceProperties().initializeDataSourceBuilder().build();
        DataSource dataSource1 = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Primary
    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource datasource) throws Exception {
        return mybatisConfigurationSupport.build(datasource);
    }

}
