package com.example.my_springboot_learn.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/proxy1")
    public String proxy1() {
        return "proxy1";
    }

    @RequestMapping(value = "/proxy2")
    public String proxy2() {
        return "proxy2";
    }




}
