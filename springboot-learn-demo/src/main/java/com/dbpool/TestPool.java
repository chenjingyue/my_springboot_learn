package com.dbpool;

import java.util.concurrent.atomic.AtomicInteger;

public class TestPool {

    private static AtomicInteger activeCount = new AtomicInteger(0);

    public static void main(String[] args) {
//        int andIncrement = activeCount.getAndIncrement();
//        System.out.println(andIncrement);
//        int i = activeCount.incrementAndGet();
//        System.out.println(i);

        DbUtil.createConnection();

    }
}
