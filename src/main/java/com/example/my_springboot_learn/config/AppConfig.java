package com.example.my_springboot_learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
@ComponentScan("com.example.my_springboot_learn.component")
public class AppConfig {


    @Bean
    public B b() {
        B b = new B();
        System.out.println("create b:" + b );
        return b ;
    }

    @Bean
    public A a() {
        System.out.println("create a");
        B b = b();
        System.out.println("b:"+b);
        A a = new A();
        a.setB(b);
        return a;
    }
}
