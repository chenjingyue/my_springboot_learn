package com.alarm.spring.boot.autoconfigure.panda;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 *  参考MapperScannerRegistrar
 */
public class PandaScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(PandaScan.class.getName());

        if (annotationAttributes != null) {
            AnnotationAttributes attributes = new AnnotationAttributes(annotationAttributes);
            String[] values = attributes.getStringArray("value");
            String[] name = attributes.getStringArray("name");
            Class<? extends Annotation> annotationClass = attributes.getClass("annotationClass");


            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

            AnnotationTypeFilter filter = new AnnotationTypeFilter(annotationClass);
            scanner.addIncludeFilter(filter);
//        scanner.addExcludeFilter();

            String[] packages = {"com.alarm.spring.boot.autoconfigure.panda"};
            scanner.scan(packages);

        }

    }
}
