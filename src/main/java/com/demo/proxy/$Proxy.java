//package com.demo.proxy;
//import java.lang.reflect.Method;
//import com.example.my_springboot_learn.proxy.ITestProxy;
//import com.example.my_springboot_learn.proxy.MyInvocationHandler;
//public class $Proxy implements ITestProxy {
//    private MyInvocationHandler target;
//    public $Proxy(MyInvocationHandler target) {
//        this.target = target;
//    }
//    public String query(String arg0) {
//        String result = null;
//        try {
//            Method method = ITestProxy.class.getMethod( "query",String.class);
//            result = (String) target.invoke(this,method,new Object[] {arg0});
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return result;
//    }
//}