package com.example.my_springboot_learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println(Arrays.toString(proxy.getClass().getFields()));
        System.out.println("MyInvocationHandler");
        return "aaa";
    }
}
