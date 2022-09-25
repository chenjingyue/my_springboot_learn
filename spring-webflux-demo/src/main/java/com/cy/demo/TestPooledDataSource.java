package com.cy.demo;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TestPooledDataSource {

    public static void main(String[] args) {
//        String sql = "select * from user_info where id = ?";
        String sql = "select * from user_info ";
        PooledDataSourceFactory factory = new PooledDataSourceFactory();
        Properties properties = new Properties();
//        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("username", "root");
        properties.setProperty("password", "chenyu123");
        properties.setProperty("url", "jdbc:mysql://192.168.106.128:3306/test1");
        factory.setProperties(properties);
        DataSource dataSource = factory.getDataSource();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getObject(2));
            }

            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
        }
    }
}
