package com.cy.springbootlearndemo.service;


import com.cy.springbootlearndemo.model.User;

import java.util.List;

public interface IUserService {
    List<User> selectAllUser() throws Exception;

    void updateUser(User user) throws Exception;

    void insertUser(User user) throws Exception;

}
