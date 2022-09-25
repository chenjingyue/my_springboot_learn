package com.cy.springbootlearndemo.component;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OrderService implements BeanFactoryAware, InitializingBean {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private String name;


    public OrderService() {
        System.out.println("default OrderService");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("@PostConstruct:init");
    }

//    @Autowired(required = false)
//    public OrderService(B b) {
//        System.out.println("OrderService----->b");
//    }


//    @Autowired(required = true)
//    public OrderService(B b, String aa) {
//        System.out.println("OrderService----->b and string");
//    }


}
