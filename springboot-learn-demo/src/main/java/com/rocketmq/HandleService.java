package com.rocketmq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandleService implements Runnable {

    private volatile boolean stop = false;
    private AtomicBoolean hasNotified = new AtomicBoolean(false);
    private CountDownLatch waitPoint = new CountDownLatch(1);

    private List<SyncRequest> readList = new ArrayList<>();
    private List<SyncRequest> writeList = new ArrayList<>();

    public void put(SyncRequest request) {
        writeList.add(request);
        this.wakeup();
    }

    public void waitForRunning(long interval) {
        if (hasNotified.compareAndSet(true, false)) {
            onWaitEnd();
            return;
        }
        waitPoint = new CountDownLatch(1);
        try {
            System.out.println(Thread.currentThread().getName() + " waiting");
            waitPoint.await(interval, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + " waiting end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            hasNotified.set(false);
            onWaitEnd();
        }

    }

    public void wakeup() {
        if (hasNotified.compareAndSet(false, true)) {
            System.out.println(Thread.currentThread().getName() + " wakeup");
            waitPoint.countDown();
        }
    }


    @Override
    public void run() {

        while (!this.stop) {
            waitForRunning(1000);
            doSomething();
        }

    }

    public void doSomething() {
        for (SyncRequest request : readList) {
            if ("OK".equals(request.getStatus())) {
                request.wakeupCustomer("OK");
            } else {
                request.wakeupCustomer("ERROR");
            }
            System.out.println(Thread.currentThread().getName() + " wakeupCustomer: " + request.getStatus());
        }
        readList.clear();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void onWaitEnd() {
        System.out.println(Thread.currentThread().getName() + " start swap");
        this.swapRequests();
    }

    public void swapRequests() {
        List<SyncRequest> tmp = this.readList;
        this.readList = writeList;
        this.writeList = tmp;
    }

    public void stop() {
        this.stop = true;
    }
}
