package com.rocketmq;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestMain {
    public static void main(String[] args) {
        HandleService service = new HandleService();
        Thread thread = new Thread(service);
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " put: " + (i % 2 == 0 ? "OK" : "ERROR") + i);
                    SyncRequest request = new SyncRequest(i % 2 == 0 ? "OK" : "ERROR");
                    service.put(request);
                    CompletableFuture<String> future = request.future();
                    try {
                        System.out.println(Thread.currentThread().getName() + " sync waiting result: ");
                        String status = future.get(1000, TimeUnit.MILLISECONDS);
                        System.out.println(Thread.currentThread().getName() + " sync result: " + status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    if (i == 0) {
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
//                service.wakeup();
            }
        });
        thread1.start();
        byte[] b = new byte[90];
        try {
            System.in.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
