package com.cy.springbootlearndemo.mapper;


import com.cy.springbootlearndemo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper{


    List<User> selectAllUser() throws Exception;

}
