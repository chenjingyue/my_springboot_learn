package com.cy.demo.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements BeanFactoryAware{


//    public void setBeanFactory(BeanFactoryAware beanFactory) {
//        this.beanFactory = beanFactory;
//    }
//   public  UserController(@Autowired BeanFactoryAware beanFactory) {
//       System.out.println(null == beanFactory);
//       System.out.println("UserController");
//    }
//
    @Autowired
    private BeanFactory beanFactory;


    @GetMapping("/user")
    public String user(@RequestParam(required = false,name = "name") String name) {
       System.out.println(null == beanFactory);
        return name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("---setBeanFactory---");
    }
}
