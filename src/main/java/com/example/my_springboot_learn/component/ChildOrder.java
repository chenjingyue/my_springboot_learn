package com.example.my_springboot_learn.component;

import org.springframework.beans.factory.annotation.Autowired;

public class ChildOrder extends OrderService{

    @Autowired
    private String name;
}
