package com.example.my_springboot_learn.config;

import com.example.my_springboot_learn.component.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        B b = ac.getBean(A.class).getB();
//        System.out.println("--------------"+ b);
//        System.out.println(ac.getBean(B.class));
//        System.out.println(ac.getBean(B.class));
//        System.out.println(ac.getBean(A.class).getB());
ac.getBean(OrderService.class);
ac.getBean(OrderService.class);
    }
}
