package com.demo.batch.db2db.mapper.slave;

import com.demo.batch.db2db.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserToDao {


    int insertUser(User user);

    int insertUserBatch(List<User> userList);
}
