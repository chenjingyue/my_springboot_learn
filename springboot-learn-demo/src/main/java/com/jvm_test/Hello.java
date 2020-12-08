package com.jvm_test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

public final class Hello implements Serializable{

    public static int a = 4;

    public static final String str = "aaa";

    public Hello() {
    }


    public static void main(String[] args) {

    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//
//    }

    public void test() throws BeansException, Exception {
        Thread thread = new Thread(() -> {
            System.out.println("aaa");
        });
//        Thread thread1 = new Thread(() -> {
//            System.out.println("aaa");
//        });
    }

    public static class A {

    }

}
