package com.demo.batch.db2db.config;

public class DataSourceHolder {

    private static ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String dataSourceName) {
        context.set(dataSourceName);
//        System.out.println(Thread.currentThread().getName() + " set dataSourceName:" + dataSourceName);
    }

    public static String get() {
        String name = context.get();
        if (null == name){
            name = "default";
        }
        return name;
    }

    public static void clear() {
        context.remove();
    }



}
