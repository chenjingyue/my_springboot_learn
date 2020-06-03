package com.example.my_springboot_learn.proxy;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        Class<?>[] interfaces = new Class[]{ITestProxy.class};
        ITestProxy proxy = (ITestProxy)Proxy.newProxyInstance(Test.class.getClassLoader(),
                interfaces, new MyInvocationHandler());

        proxy.query();
    }
}
