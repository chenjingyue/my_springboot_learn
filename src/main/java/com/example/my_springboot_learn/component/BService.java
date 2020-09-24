package com.example.my_springboot_learn.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BService {

    @Bean
    public AService aService() {
        return new AService();
    }

    @Bean
    public AService bService() {
        return new AService();
    }
}
