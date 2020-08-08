package com.example.my_springboot_learn.component;


import com.example.my_springboot_learn.config.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@Component
@Scope("prototype")
public class OrderService {

    @Autowired
    private String name;


    public OrderService() {
        System.out.println("default OrderService");
    }

    @Autowired(required = false)
    public OrderService(B b) {
        System.out.println("OrderService----->b");
    }


//    @Autowired(required = true)
//    public OrderService(B b, String aa) {
//        System.out.println("OrderService----->b and string");
//    }


}
