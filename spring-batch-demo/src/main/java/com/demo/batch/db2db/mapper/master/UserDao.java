package com.demo.batch.db2db.mapper.master;

import com.demo.batch.db2db.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> selectAllUser();

    int insertUser(User user);



    int insertUserBatch(List<User> userList);
}
