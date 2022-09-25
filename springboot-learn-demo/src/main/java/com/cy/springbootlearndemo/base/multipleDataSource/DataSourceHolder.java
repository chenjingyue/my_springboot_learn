package com.cy.springbootlearndemo.base.multipleDataSource;

public class DataSourceHolder {

    private static ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String dataSourceName) {
        context.set(dataSourceName);
        System.out.println(Thread.currentThread().getName() + " set dataSourceName:" + dataSourceName);
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }



}
