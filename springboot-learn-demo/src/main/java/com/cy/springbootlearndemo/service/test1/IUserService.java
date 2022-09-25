package com.cy.springbootlearndemo.service.test1;

import com.cy.springbootlearndemo.model.User;
import java.util.List;

public interface IUserService {
    List<User> selectAllUser() throws Exception;
    void testTransactional() throws Exception;
    void updateUser(User user) throws Exception;
    void insertUser(User user) throws Exception;

}
