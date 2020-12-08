package com.cy.springbootlearndemo.proxy;

import com.cy.springbootlearndemo.annotation.IsInheritedAnnotation;
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
