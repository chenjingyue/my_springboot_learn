package com.cy.springbootlearndemo.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {




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
