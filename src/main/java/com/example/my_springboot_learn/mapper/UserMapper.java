package com.example.my_springboot_learn.mapper;


import com.example.my_springboot_learn.model.QueryCondition;
import com.example.my_springboot_learn.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> selectAllUser() throws Exception;

    User selectUserById(int id) throws Exception;

    void updateUserById(User user) throws Exception;

    void insertUser(User user) throws Exception;

    List<User> queryByCondition(QueryCondition queryCondition) throws Exception;

    List<User> queryByLimit(Map<String, Integer> map) throws Exception;
}
