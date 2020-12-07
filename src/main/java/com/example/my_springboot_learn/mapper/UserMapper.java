package com.example.my_springboot_learn.mapper;


import com.example.my_springboot_learn.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper{


    List<User> selectAllUser() throws Exception;

}
