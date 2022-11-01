package com.demo.batch.db2db.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration("dataSourceMap")
@ConfigurationProperties("spring")
public class DataSourceMapFactoryBean implements FactoryBean<Map<String, Object>>, InitializingBean {

    private Map<String, Object> datasource = new HashMap<>();

    private Map<String, Object> resolvedDatasource = new HashMap<>();

    @Override
    public Map<String, Object> getObject() throws Exception {
        return resolvedDatasource;
    }

    @Override
    public Class<?> getObjectType() {
        return Map.class;
    }

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

    public DataSource createDatasource(Object value) throws Exception {
        DataSource result = null;
        if (value instanceof Map) {
            Map<String, String> map = (Map<String, String>) value;
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.afterPropertiesSet();
            dataSourceProperties.setDriverClassName(map.get("driverClassName"));
            dataSourceProperties.setUrl(map.get("url"));
            dataSourceProperties.setUsername(map.get("username"));
            dataSourceProperties.setPassword(map.get("password"));
            result = dataSourceProperties.initializeDataSourceBuilder().build();
        }
        return result;
    }

    public Map<String, Object> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Object> datasource) {
        this.datasource = datasource;
    }

    public Map<String, Object> getResolvedDatasource() {
        return resolvedDatasource;
    }

    public void setResolvedDatasource(Map<String, Object> resolvedDatasource) {
        this.resolvedDatasource = resolvedDatasource;
    }
}
