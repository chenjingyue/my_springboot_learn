package com.example.my_springboot_learn.proxy;

public class TestProxy implements ITestProxy {

    @Override
    public String query(String name) {
        System.out.println("woshihhh:" + name);
        return "aaa";
    }
}
