package com.alarm.spring.boot.autoconfigure.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Map;

public class PandaScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(PandaScan.class.getName());

        if (annotationAttributes != null) {
            AnnotationAttributes attributes = new AnnotationAttributes(annotationAttributes);
            String[] values = attributes.getStringArray("value");
            String[] name = attributes.getStringArray("name");


            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

            Object annotationClass = annotationAttributes.get("annotationClass");
            AnnotationTypeFilter filter = new AnnotationTypeFilter(Panda.class);
            scanner.addIncludeFilter(filter);
//        scanner.addExcludeFilter();

            String[] packages = {"com.alarm.spring.boot.autoconfigure.panda"};
            scanner.scan(packages);

        }

    }
}
