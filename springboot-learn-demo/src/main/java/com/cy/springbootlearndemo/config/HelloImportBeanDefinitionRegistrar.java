package com.cy.springbootlearndemo.config;

import com.cy.springbootlearndemo.model.QueryCondition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class HelloImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    //
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(QueryCondition.class);
        beanDefinition.setBeanClassName(QueryCondition.class.getName());
        registry.registerBeanDefinition("queryCondition1",beanDefinition);
    }
}
