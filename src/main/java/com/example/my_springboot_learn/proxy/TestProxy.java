package com.example.my_springboot_learn.proxy;

public class TestProxy implements ITestProxy {

    @Override
    public String query() {
        System.out.println("woshihhh");
        return "aaa";
    }
}
