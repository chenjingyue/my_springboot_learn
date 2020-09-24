package com.test;

import com.object.size.CountSimpleObjectSize;
import org.openjdk.jol.info.ClassLayout;

public class BiasLock {


    static Object obj;
    private int i = 0;

    public static void main(String[] args) {

        try {
            Thread.sleep(4500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = new Object();
        BiasLock biasLock = new BiasLock();
        System.out.printf("---- before lock ----\n" + ClassLayout.parseInstance(obj).toPrintable());
        biasLock.startThread1();
        biasLock.startThread2();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("---- after lock ----\n" + ClassLayout.parseInstance(obj).toPrintable());
//        biasLock.startThread2();
    }

    public void startThread1() {
        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Thread1 id: " + Thread.currentThread().getId());
            System.out.printf("----before startThread1 ----\n" + ClassLayout.parseInstance(obj).toPrintable());
            sync();
            System.out.printf("----after startThread1 ----\n" + ClassLayout.parseInstance(obj).toPrintable());

//
//            System.out.printf("----before startThread1 ---2-\n" + ClassLayout.parseInstance(obj).toPrintable());
//            sync();
//            System.out.printf("----after startThread1 --2--\n" + ClassLayout.parseInstance(obj).toPrintable());
        }).start();
    }

    public void startThread2() {
        new Thread(() -> {
            try {
                Thread.sleep(3100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("----before startThread2 ----\n" + ClassLayout.parseInstance(obj).toPrintable());
            sync();
            System.out.printf("----after startThread2 ----\n" + ClassLayout.parseInstance(obj).toPrintable());
        }).start();
    }

    public void sync() {
        synchronized (obj) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            i++;
            System.out.printf("---- sync ing ----\n" + ClassLayout.parseInstance(obj).toPrintable());
        }
    }

}
