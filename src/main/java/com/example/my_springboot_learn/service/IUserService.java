package com.example.my_springboot_learn.service;


import com.example.my_springboot_learn.model.User;

import java.util.List;

public interface IUserService {
    List<User> selectAllUser() throws Exception;


}
