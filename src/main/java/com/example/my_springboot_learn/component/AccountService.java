package com.example.my_springboot_learn.component;


import com.example.my_springboot_learn.config.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class AccountService {


    @Autowired
    private OrderService orderService;


    public AccountService() {
        System.out.println("default OrderService");
    }

//    @Autowired(required = false)
//    public UserService(B b) {
//        System.out.println("OrderService----->b");
//    }


//    @Autowired(required = true)
//    public OrderService(B b, String aa) {
//        System.out.println("OrderService----->b and string");
//    }


}
