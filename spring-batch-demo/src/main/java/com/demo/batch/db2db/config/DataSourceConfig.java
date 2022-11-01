package com.demo.batch.db2db.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.demo.batch.db2db.mapper", annotationClass = Mapper.class, sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {


    @Primary
    @Bean(name = "hsqldbDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.hsqldb")
    public DataSourceProperties hsqldbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "hsqldbDataSource")
    @Primary
    public DataSource hsqldbDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = hsqldbDataSourceProperties().initializeDataSourceBuilder();
        DataSource dataSource = dataSourceBuilder.build();
//        DataSource dataSource1 = DataSourceBuilder.create().build();
        return dataSource;
    }


    /**
     * MySql多数据源配置
     */
    @Bean("multipleDataSource")
    public DataSource dataSource(Map<Object, Object> dataSourceMap) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        Object defaultDataSource = dataSourceMap.get("default");
        if (null != defaultDataSource) {
            dataSourceRouter.setDefaultTargetDataSource(defaultDataSource);
        }
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        return dataSourceRouter;
    }

    /* MybatisAutoConfiguration.sqlSessionFactory */
    @Bean
    public SqlSessionFactory SqlSessionFactory(@Qualifier("multipleDataSource") DataSource dataSource,
                                               MybatisProperties properties,
                                               ResourceLoader resourceLoader) throws Exception {
        SqlSessionFactoryBean factory = getSqlSessionFactoryBean(dataSource, properties, resourceLoader);
        return factory.getObject();
    }

    private SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource, MybatisProperties properties, ResourceLoader resourceLoader) {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setDefaultExecutorType(ExecutorType.BATCH);
//        factory.setConfiguration(configuration);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
        }
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }
        return factory;
    }


//    @Bean(name = "mysqlDataSource")
//    public DataSource mysqlDataSource(){
//        DataSourceBuilder<?> root = DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/test").username("root").password("root");
//        DataSource dataSource = root.build();
//        return dataSource;
//
//    }
//
//    @Bean(name = "hsqldbDataSource")
//    public DataSource hsqldbDataSource(){
//        DataSourceBuilder<?> root = DataSourceBuilder.create().driverClassName("org.hsqldb.jdbc.JDBCDriver")
//                .url("jdbc:mysql://localhost:3306/test").username("root").password("root");
//        DataSource dataSource = root.build();
//        return dataSource;
//
//    }


//    @Bean(name = "mysqlMasterDataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.mysql.master")
//    public DataSourceProperties mysqlMasterDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "mysqlMasterDataSource")
//    public DataSource mysqlMasterDataSource() {
//        DataSource dataSource = mysqlMasterDataSourceProperties().initializeDataSourceBuilder().build();
////        DataSource dataSource1 = DataSourceBuilder.create().build();
//        return dataSource;
//    }
//
//
//    @Bean(name = "mysqlSlaveDataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.mysql.slave")
//    public DataSourceProperties mysqlSlaveDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "mysqlSlaveDataSource")
//    public DataSource mysqlSlaveDataSource() {
//        DataSource dataSource = mysqlSlaveDataSourceProperties().initializeDataSourceBuilder().build();
////        DataSource dataSource1 = DataSourceBuilder.create().build();
//        return dataSource;
//    }


//
//    @Bean
//    public SqlSessionFactory mysqlSlaveSqlSessionFactory(@Qualifier("mysqlSlaveDataSource") DataSource mysqlSlaveDataSource,
//                                                         MybatisProperties properties,
//                                                         ResourceLoader resourceLoader) throws Exception {
//        SqlSessionFactoryBean factory = getSqlSessionFactoryBean(mysqlSlaveDataSource, properties, resourceLoader);
//        return factory.getObject();
//    }
}
