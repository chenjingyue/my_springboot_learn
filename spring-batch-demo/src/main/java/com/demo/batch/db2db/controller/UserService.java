package com.demo.batch.db2db.controller;

import com.demo.batch.db2db.config.DataSourceHolder;
import com.demo.batch.db2db.entity.User;
import com.demo.batch.db2db.mapper.slave.UserToDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserToDao userToDao;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    @Qualifier("multipleDataSource")
    private DataSource dataSource;

    @Transactional(value = "multiplePlatformTransactionManager")
    public void insertUser() {
        SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
//        System.out.println(holder);
//        ConnectionHolder holder1 = (ConnectionHolder) TransactionSynchronizationManager.unbindResource(dataSource);
//        System.out.println(holder1);
        User user = new User();
        user.setName("test");
        user.setAge(11);
        List<User> list = new ArrayList<>();
        list.add(user);

        /** 事务中切换数据源没有效果 */
        DataSourceHolder.set("master");
        userToDao.insertUserBatch(list);
//        ConnectionHolder holder2 = (ConnectionHolder) TransactionSynchronizationManager.unbindResource(dataSource);
//        TransactionSynchronizationManager.bindResource(dataSource, holder1);
//        throw new RuntimeException("eeeeeeeeeeeee");
        System.out.println("qqq");
    }
}
