package com.example.my_springboot_learn.web;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
//@Component
public class MyServletContextInitializer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        System.out.println("springboot 启动的是否会调用");

        // Load Spring web application configuration
//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        ac.register(AppConfig.class);
//        ac.refresh();
//
//        // Create and register the DispatcherServlet
//        DispatcherServlet servlet = new DispatcherServlet(ac);
//        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");

    }
}
