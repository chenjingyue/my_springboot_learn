package com.cy.springbootlearndemo.config.mysql.many;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.boot.jta.atomikos.AtomikosXADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.sql.XADataSource;

/**
 * 多数据源事务控制
 */
//@Configuration
public class XaDataSourceConfig  implements BeanClassLoaderAware {

    private ClassLoader beanClassLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
    protected XADataSource createXaDataSource(DataSourceProperties properties) {
        String className = properties.getXa().getDataSourceClassName();
        if (!StringUtils.hasLength(className)) {
            className = DatabaseDriver.fromJdbcUrl(properties.determineUrl())
                    .getXaDataSourceClassName();
        }
        Assert.state(StringUtils.hasLength(className),
                "No XA DataSource class name specified");
        XADataSource dataSource = createXaDataSourceInstance(className);
        bindXaProperties(dataSource, properties);
        return dataSource;
    }

    private XADataSource createXaDataSourceInstance(String className) {
        try {
            Class<?> dataSourceClass = ClassUtils.forName(className, this.beanClassLoader);
            Object instance = BeanUtils.instantiateClass(dataSourceClass);
            Assert.isInstanceOf(XADataSource.class, instance);
            return (XADataSource) instance;
        }
        catch (Exception ex) {
            throw new IllegalStateException(
                    "Unable to create XADataSource instance from '" + className + "'");
        }
    }

    private void bindXaProperties(XADataSource target,
                                  DataSourceProperties dataSourceProperties) {
        Binder binder = new Binder(getBinderSource(dataSourceProperties));
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(target));
    }

    private ConfigurationPropertySource getBinderSource(
            DataSourceProperties dataSourceProperties) {
        MapConfigurationPropertySource source = new MapConfigurationPropertySource();
        source.put("user", dataSourceProperties.determineUsername());
        source.put("password", dataSourceProperties.determinePassword());
        source.put("url", dataSourceProperties.determineUrl());
        source.putAll(dataSourceProperties.getXa().getProperties());
        ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
        aliases.addAliases("user", "username");
        return source.withAliases(aliases);
    }


    @Autowired
    XADataSourceWrapper wrapper;

    @Bean
    public AtomikosXADataSourceWrapper xaDataSourceWrapper() {

        return new AtomikosXADataSourceWrapper();
    }


//    @Bean(name = "test1DataSourceProperties")
//    @Primary
//    @ConfigurationProperties("spring.datasource.test1")
//    public DataSourceProperties getDataSourceProperties() {
//        return new DataSourceProperties();
//    }

//    @Bean(name = "test1DataSource")
//    @Primary
//    public DataSource getDataSource() throws Exception {
//        XADataSource xaDataSource = createXaDataSource(getDataSourceProperties());
//        return this.wrapper.wrapDataSource(xaDataSource);
//    }

//    @Bean(name = "test2DataSourceProperties")
//    @ConfigurationProperties("spring.datasource.test2")
//    public DataSourceProperties getDataSourceProperties2() {
//        return new DataSourceProperties();
//    }
//
//    @Bean(name = "test2DataSource")
//    public DataSource getDataSource2() throws Exception {
//        XADataSource xaDataSource = createXaDataSource(getDataSourceProperties2());
//        return this.wrapper.wrapDataSource(xaDataSource);
//    }


    /**
     * 注入事物管理器
     *
     * @return
     */
//    @Bean(name = "xatx")
//    public JtaTransactionManager regTransactionManager() {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        UserTransaction userTransaction = new UserTransactionImp();
//        return new JtaTransactionManager(userTransaction, userTransactionManager);
//    }
}
