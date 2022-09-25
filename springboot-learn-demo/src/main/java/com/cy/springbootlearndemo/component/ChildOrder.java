package com.cy.springbootlearndemo.component;

import org.springframework.beans.factory.annotation.Autowired;

public class ChildOrder extends OrderService{

    @Autowired
    private String name;
}
