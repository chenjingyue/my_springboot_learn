package com.dbpool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DbUtil {

    private static String driverClass = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.106.128:3306/test";
    private static String username = "root";
    private static String password = "root";

    public static Connection createConnection() {
        Connection connection = null;
        try {
            Class<?> aClass = Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void executeSelect(Connection connection, String sql) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<User> list = new ArrayList<>();
            User user;
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                user = new User();
                user.setId(id);
                user.setName(name);
                list.add(user);
            }
            System.out.println("结果：" + list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
                if (null != resultSet) {
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        int threadCount = 300;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        DbPoolImpl dbPool = new DbPoolImpl(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
//            Connection connection = dbPool.getConnection();
//            DbUtil.executeSelect(connection,"select * from user;");
//            dbPool.releaseConnection(connection);
//            countDownLatch.countDown();

            Thread thread = new Thread(() -> {
                Connection connection = dbPool.getConnection();
                DbUtil.executeSelect(connection,"select * from user;");
                dbPool.releaseConnection(connection);
                countDownLatch.countDown();
            });
            thread.start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("使用时间："+(end - start));
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("--------------------------");
//        for (int i = 0; i < 20; i++) {
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
////                    System.out.println(Thread.currentThread().getName() + ": ");
//                    Connection connection = dbPool.getConnection();
//                    DbUtil.executeSelect(connection,"select * from user;");
//                    dbPool.releaseConnection(connection);
//                }
//            };
//            thread.start();
//        }
//        System.out.println("数量" + dbPool.count());
    }
}
