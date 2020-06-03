package com.example.my_springboot_learn.service.impl;

import com.example.my_springboot_learn.mapper.UserMapper;
import com.example.my_springboot_learn.model.QueryCondition;
import com.example.my_springboot_learn.model.User;
import com.example.my_springboot_learn.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUser() throws Exception {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUserById(int id) throws Exception {
        return userMapper.selectUserById(id);
    }

    @Override
    public void updateUserById(User user) throws Exception {
        userMapper.updateUserById(user);
    }

    @Override
    public void insertUser(User user) throws Exception {
            userMapper.insertUser(user);
    }

    @Override
    public List<User> queryByCondition(QueryCondition queryCondition) throws Exception {


        Integer pageNow = queryCondition.getPageNow();
        Integer pageSize = queryCondition.getPageSize();
        if (null != pageNow && null != pageSize) {
            int start = (pageNow - 1) * pageSize;
            queryCondition.setStart(start);
        }

        return userMapper.queryByCondition(queryCondition);
    }

    @Override
    public List<User> queryByLimit(int page, int pageSize) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        int start = (page - 1) * pageSize;
        map.put("start", start);
        map.put("pageSize", pageSize);
        return userMapper.queryByLimit(map);
    }


}
