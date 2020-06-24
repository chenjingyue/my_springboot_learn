package com.example.my_springboot_learn.proxy;

import com.example.my_springboot_learn.annotation.IsInheritedAnnotation;
import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;

public class Test {

    public static void main(String[] args) {
//        Class<?>[] interfaces = new Class[]{ITestProxy.class};
//        ITestProxy proxy = (ITestProxy)Proxy.newProxyInstance(Test.class.getClassLoader(),
//                interfaces, new MyInvocationHandler());
//
//        proxy.query();
        String name = Component.class.getName();
        System.out.println(IsInheritedAnnotation.class.isAnnotationPresent(Inherited.class));

//        proxy.query("shhh");
    }
}
