package com.cy.springbootlearndemo.web;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;

public class MyWebApplicationInitializer implements ServletContextInitializer {

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
