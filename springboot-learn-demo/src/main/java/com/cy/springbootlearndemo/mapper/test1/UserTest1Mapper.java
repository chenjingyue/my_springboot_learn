package com.cy.springbootlearndemo.mapper.test1;


import com.cy.springbootlearndemo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTest1Mapper {
    List<User> selectAllUser() throws Exception;
    void updateUser(User user) throws Exception;
    void insertUser(User user) throws Exception;


}
