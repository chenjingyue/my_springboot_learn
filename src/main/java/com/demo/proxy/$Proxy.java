package com.demo.proxy;

import com.example.my_springboot_learn.proxy.ITestProxy;
import com.example.my_springboot_learn.proxy.MyInvocationHandler;

import java.lang.reflect.Method;

public class $Proxy implements ITestProxy {
    private MyInvocationHandler target;

    public $Proxy(MyInvocationHandler target) {
        this.target = target;
    }

    public String query() {
        Object proxy = this;
        Method method = null;
        Object[] args = null;
        try {
            method = this.getClass().getMethod("query");
            return (String) target.invoke(proxy, method, args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}