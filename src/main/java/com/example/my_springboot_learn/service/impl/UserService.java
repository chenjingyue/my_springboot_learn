package com.example.my_springboot_learn.service.impl;

import com.example.my_springboot_learn.mapper.UserMapper;
import com.example.my_springboot_learn.model.QueryCondition;
import com.example.my_springboot_learn.model.User;
import com.example.my_springboot_learn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
//    @Cacheable(value = "user", keyGenerator = "myKeyGenerator")
    public List<User> selectAllUser() throws Exception {
        return userMapper.selectAllUser();
    }



}
