package com.example.my_springboot_learn;

import com.example.my_springboot_learn.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        ac.register(AppConfig.class);
        ac.refresh();

    }
}
