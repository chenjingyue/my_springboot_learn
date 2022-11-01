package com.demo.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BatchProcessingApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(BatchProcessingApplication.class, args);

//        System.exit(SpringApplication.exit(run));
    }
    /**
     *  <dependency>
     *      <groupId>org.springframework.cloud</groupId>
     *      <artifactId>spring-cloud-starter-openfeign</artifactId>
     *  </dependency>
     * <p>
     * 引入这个依赖会导致注入 DataSourceConfiguration$Hikari.class 时报错:
     * Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.support.BeanDefinitionOverrideException: Invalid bean definition with name 'dataSource' defined in BeanDefinition defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Cannot register bean definition [Root bean: class [org.springframework.aop.scope.ScopedProxyFactoryBean];
     * scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in BeanDefinition defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]] for bean 'dataSource': There is already [Root bean: class [null]; scope=refresh; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=false; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration$Hikari; factoryMethodName=dataSource; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]] bound.
     * <p>
     *  The bean 'dataSource', defined in BeanDefinition defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class],
     *  could not be registered. A bean with that name has already been defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class] and overriding is disabled.
     * <p>
     *
     * 报错代码：
     * RefreshScopeBeanDefinitionEnhancer
     * 		RefreshAutoConfiguration.RefreshScopeBeanDefinitionEnhancer#postProcessBeanDefinitionRegistry()
     * <p>
     *
     */
}