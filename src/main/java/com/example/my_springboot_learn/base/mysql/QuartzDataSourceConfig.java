package com.example.my_springboot_learn.base.mysql;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;


//@Configuration
@MapperScan(annotationClass = QuartzMapper.class, basePackages = {"com.yg.education.web.dao.quartz"}, sqlSessionFactoryRef =
        "quartzSqlSessionFactory")
public class QuartzDataSourceConfig {

    @Resource
    private MybatisConfigurationSupport mybatisConfigurationSupport;

    @Bean(name = "quartzDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.quartz")
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "quartzSqlSessionFactory")
    public SqlSessionFactory quartzSqlSessionFactory(@Qualifier("quartzDataSource") DataSource quartzDatasource) throws Exception {
        return mybatisConfigurationSupport.build(quartzDatasource);
    }

}
