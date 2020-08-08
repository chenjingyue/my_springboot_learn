package com.jvm_test;

import com.jvm_test.constant.ConstantMethodrefInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.Serializable;

public final class Hello   implements Serializable,ApplicationContextAware {

    public static int a = 4;

    public ConstantMethodrefInfo str;

    public static void main(String[] args) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
    
    public  static class A{

    }
}
