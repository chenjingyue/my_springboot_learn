package com.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

    public static void main(String[] args) {
         ExecutorService executorService1 = Executors.newFixedThreadPool(10);
         ExecutorService executorService2 = Executors.newCachedThreadPool();
         ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100; i++) {
            executorService1.execute(new MyTask(i));
        }

//        final Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()+"--"));
//        thread.start();
    }

}

class MyTask implements Runnable{

    private int i;

    public MyTask(int i ) {
        this.i = i;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"--" +i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
