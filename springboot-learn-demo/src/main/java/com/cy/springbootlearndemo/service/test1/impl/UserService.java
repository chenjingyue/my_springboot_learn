package com.cy.springbootlearndemo.service.test1.impl;

import com.cy.springbootlearndemo.mapper.test1.UserTest1Mapper;
import com.cy.springbootlearndemo.mapper.test2.UserTest2Mapper;
import com.cy.springbootlearndemo.model.User;
import com.cy.springbootlearndemo.service.test1.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServcieTest1")
//@Transactional("test2TransactionManager")
//@Transactional()
public class UserService implements IUserService {

    @Autowired
    private UserTest1Mapper userTest1Mapper;
    @Autowired
    private UserTest2Mapper userTest2Mapper;

    @Override
    public List<User> selectAllUser() throws Exception {
        return userTest1Mapper.selectAllUser();
    }

    @Override
    @Transactional(transactionManager = "xatx",
            rollbackFor = {java.lang.Exception.class})
    public void testTransactional() throws Exception {
        User user = new User();
        user.setA(1);
        user.setB(1);
        user.setC(1);
        user.setD(1);
        user.setUsername("ttt");
        user.setPassword("ttt");
        userTest1Mapper.insertUser(user);
        System.out.println("test1 数据新增完成");

        userTest2Mapper.insertUser(user);
        System.out.println("test2 数据新增完成");
//        int a = 1 / 0;


    }

    @Override
    public void updateUser(User user) throws Exception {
        userTest1Mapper.updateUser(user);
    }

    @Override
    public void insertUser(User user) throws Exception {
        userTest1Mapper.insertUser(user);
    }


}
