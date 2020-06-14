package com.example.my_springboot_learn.mapper;


import com.example.my_springboot_learn.model.QueryCondition;
import com.example.my_springboot_learn.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> selectAllUser() throws Exception;

}
