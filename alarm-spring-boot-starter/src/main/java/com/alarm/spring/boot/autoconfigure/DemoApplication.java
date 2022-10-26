package com.alarm.spring.boot.autoconfigure;

import com.alarm.spring.boot.autoconfigure.panda.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Constructor;

@SpringBootApplication(scanBasePackages = "com.alarm.spring.boot.autoconfigure.scan")
//@PandaScan(value = "com.alarm.spring.boot.autoconfigure.panda",name = "hahaha")
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
//        test();

    }

    public static void test() {
        Class<Test> testClass = Test.class;
        try {
            Constructor<Test> declaredConstructor = testClass.getDeclaredConstructor();
            System.out.println("--------------");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


    }




}
