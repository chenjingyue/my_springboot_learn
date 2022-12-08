package com.demo.batch.h2;


import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class TestH2 {

    public static void main(String[] args) {
        String driverClass = "org.h2.Driver";
        String url = "jdbc:h2:file:G:/chenyu/h2/bbb";
        String JDBC_URL = "jdbc:h2:tcp://localhost:9125/G:/chenyu/h2/aaa;CACHE_SIZE=32384;MAX_LOG_SIZE=32384;mv_store=true";
        String JDBC_URL1 = "jdbc:h2:mem:bbb";
        String username = "sa";
        String password = "";
        String sql = "";
        try {

//            Server server = Server.createTcpServer(
//                    "-tcp","-tcpAllowOthers","-tcpPort", "9125").start();

            Class.forName(driverClass);
            Connection connect = DriverManager.getConnection(url, username, password);
            Statement stmt = connect.createStatement();
            stmt.execute("DROP TABLE IF EXISTS USER_INFO");
            //创建USER_INFO表
            stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
            //新增
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','如来','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','青龙','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','白虎','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','朱雀','女')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','玄武','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID() + "','苍狼','男')");
            //删除
            stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
            //修改
            stmt.executeUpdate("UPDATE USER_INFO SET name='猪猪' WHERE name='苍狼'");
            //查询
            ResultSet rs = stmt.executeQuery("SELECT * FROM USER_INFO");
            //遍历结果集
            while (rs.next()) {
                System.out.println(rs.getString("id") + "," + rs.getString("name") + "," + rs.getString("sex"));
            }
            rs.close();
            //释放资源
            stmt.close();
            //关闭连接
            connect.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        thread();
    }

    private static void thread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();
    }
}
