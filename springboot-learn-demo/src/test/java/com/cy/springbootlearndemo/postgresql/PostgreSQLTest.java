package com.cy.springbootlearndemo.postgresql;

import com.cy.springbootlearndemo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PostgreSQLTest {

//    @Autowired
//    private DataSource dataSource;

    @Test
    public void testDataSource() {
//        DataSource build = new DataSourceProperties()
//                .initializeDataSourceBuilder().build();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://192.168.106.130:5432/testdb")
                .username("postgres")
                .password("postgres").build();
        String sql = "SELECT id, username, password FROM public.\"user\";";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                list.add(user);
            }
            System.out.println(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (null != resultSet) {
                    resultSet.close();
                }
                if (null != preparedStatement) {
                    preparedStatement.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
