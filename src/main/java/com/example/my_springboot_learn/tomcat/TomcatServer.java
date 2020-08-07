//package com.example.my_springboot_learn.tomcat;
//
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.connector.Connector;
//import org.apache.catalina.startup.Tomcat;
//
//public class TomcatServer {
//    public static void main(String[] args) {
//        Tomcat tomcat = new Tomcat();
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setPort(8888);
//        tomcat.setConnector(connector);
//
////		tomcat.setPort(8888);
////		tomcat.setBaseDir("G:\\log\\");
////		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
////		tomcat.getService().addConnector(connector);
////		tomcat.addContext("/app","G:\\log\\");
//
//        tomcat.addWebapp("/app", "G:\\log\\");
//        try {
//            tomcat.start();
//            tomcat.getServer().await();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
//    }
//}
