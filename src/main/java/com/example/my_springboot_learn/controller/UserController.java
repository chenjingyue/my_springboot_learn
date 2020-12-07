package com.example.my_springboot_learn.controller;


import com.example.my_springboot_learn.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/proxy1")
    public String proxy1() {
        try {
            System.out.println(userService.selectAllUser());
        } catch (Exception e) {
            return e.getMessage();
        }
        return "proxy1";
    }

    @RequestMapping(value = "/proxy2")
    public String proxy2() {
        return "proxy2";
    }




}
