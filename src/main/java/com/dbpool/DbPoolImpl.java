package com.dbpool;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DbPoolImpl implements DbPool {


    // 空闲连接池
    private BlockingQueue<Connection> idleConnectPool;

    // 活跃连接池
    private BlockingQueue<Connection> busyConnectPool;

    // 活跃连接数量
    private final AtomicInteger activeCount = new AtomicInteger();

    // 最大连接数
    private final int maxSize;

    public DbPoolImpl(int maxSize) {
        this.maxSize = maxSize;
        this.init();
    }

    @Override
    public void init() {
        this.idleConnectPool = new LinkedBlockingQueue<>();
        this.busyConnectPool = new LinkedBlockingQueue<>();
    }

    @Override
    public Connection getConnection() {
        Connection connection = idleConnectPool.poll();

        //从空闲池中获取一个连接 添加到活跃池中
        if (null != connection) {
            System.out.println("从空闲池中获取一个连接");
            busyConnectPool.offer(connection);
            return connection;
        }

//        synchronized (this) {
        // 如果活跃连接数小于最大连接数，创建一个新的连接
        if (this.activeCount.get() < this.maxSize) {
            if (activeCount.incrementAndGet() <= this.maxSize) {
                System.out.println("创建一个新的连接. 当前活跃连接数：" + this.activeCount.get());
                connection = DbUtil.createConnection();
                this.busyConnectPool.offer(connection);
                return connection;
            }
//            activeCount.decrementAndGet();
        }

        // 如果活跃连接数大于最大连接数，等待是否有连接释放
        try {
            System.out.println("活跃连接个数：" + this.activeCount.get());
            connection = idleConnectPool.poll(10, TimeUnit.SECONDS);
            if (null == connection) {
                System.out.println("等待超时");
                throw new RuntimeException("等待连接超时");
            }
            System.out.println("等待到了一个连接");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return connection;
    }


    @Override
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            busyConnectPool.remove(connection);
            idleConnectPool.offer(connection);
        }
    }

    @Override
    public void destroy() {

    }

    public int count() {
        return this.busyConnectPool.size();
    }
}
