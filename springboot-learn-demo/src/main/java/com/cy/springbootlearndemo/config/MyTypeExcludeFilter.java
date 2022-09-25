package com.cy.springbootlearndemo.config;

import com.cy.springbootlearndemo.model.QueryCondition;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyTypeExcludeFilter extends TypeExcludeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (QueryCondition.class.getName().equals(metadataReader.getClassMetadata().getClassName())) {
            return true;
        }
        return false;
    }
}
