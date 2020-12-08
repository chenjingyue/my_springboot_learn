package com.dbpool;

import java.sql.Connection;

public interface DbPool {

    // 初始化连接池
    void init();

    // 获取连接池
    Connection getConnection();

    // 释放连接池
    void releaseConnection(Connection connection);

    // 销毁连接池
    void destroy();

}
