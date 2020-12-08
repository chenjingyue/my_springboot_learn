package com.cy.springbootlearndemo.service.impl;

import com.cy.springbootlearndemo.config.mysql.separate.DBContextHolder;
import com.cy.springbootlearndemo.mapper.UserMapper;
import com.cy.springbootlearndemo.mapper.test2.UserTest2Mapper;
import com.cy.springbootlearndemo.model.User;
import com.cy.springbootlearndemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional("test2TransactionManager")
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private int i = 0;

    @Override
    public List<User> selectAllUser() throws Exception {

        DBContextHolder.slave();
        return userMapper.selectAllUser();
    }

    @Override
    public void updateUser(User user) throws Exception {
//        userMapper.updateUser(user);
    }

    @Override
    public void insertUser(User user) throws Exception {
//        userMapper.insertUser(user);
    }


}
