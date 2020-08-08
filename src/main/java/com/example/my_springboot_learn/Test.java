package com.example.my_springboot_learn;

import com.example.my_springboot_learn.component.AService;
import com.example.my_springboot_learn.component.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.my_springboot_learn.component")
public class Test {


    public AService getAaa() {
        return aaa;
    }

    @Autowired
    private AService aaa;
//
//    @Autowired
//    private BService aService;



    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Test.class);
        Test bean = ac.getBean(Test.class);
        System.out.println(bean.getAaa());
    }

}
