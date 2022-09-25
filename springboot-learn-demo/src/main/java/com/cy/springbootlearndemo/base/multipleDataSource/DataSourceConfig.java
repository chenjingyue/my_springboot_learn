package com.cy.springbootlearndemo.base.multipleDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableConfigurationProperties
@MapperScan(basePackages = {"com.cy.springbootlearndemo.mapper"}, sqlSessionFactoryRef = "multipleSqlSessionFactory")
public class DataSourceConfig {


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

    @ConfigurationProperties("spring")
    @Configuration("dataSourceMap")
    public static class DataSourceMapFactoryBean implements FactoryBean<Map<String, Object>>, InitializingBean {

        private Map<String, Object> datasource = new HashMap<>();
        private Map<String, Object> resolvedDatasource = new HashMap<>();

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.notNull(this.datasource, "datasource is null");
            Map<String, Object> resolvedDatasourceMap = new HashMap<>(this.datasource.size());
            for (Map.Entry<String, Object> entry : datasource.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                DataSource resolvedDatasource = createDatasource(value);
                resolvedDatasourceMap.put(key, resolvedDatasource);
            }
            this.resolvedDatasource = resolvedDatasourceMap;
        }

        public DataSource createDatasource(Object value) {
            DataSource result = null;
            if (value instanceof Map) {
                Map<String, String> map = (Map<String, String>) value;
                result = DataSourceBuilder.create()
//                        .driverClassName(map.get("driverClassName"))
                        .url(map.get("jdbc-url"))
                        .username(map.get("username"))
                        .password(map.get("password"))
                        .build();
            }
            return result;
        }

        @Override
        public Map getObject() throws Exception {
            return this.resolvedDatasource;
        }

        @Override
        public Class<?> getObjectType() {
            return Map.class;
        }

        public Map<String, Object> getDatasource() {
            return datasource;
        }

        public void setDatasource(Map<String, Object> datasource) {
            this.datasource = datasource;
        }
    }

    @Bean(name = "multipleSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("multipleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        bean.setPlugins(new Interceptor[]{mybatisInterceptor});
        return bean.getObject();
    }


    @Bean(name = "multiplePlatformTransactionManager")
    public DataSourceTransactionManager platformTransactionManager(@Qualifier("multipleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
