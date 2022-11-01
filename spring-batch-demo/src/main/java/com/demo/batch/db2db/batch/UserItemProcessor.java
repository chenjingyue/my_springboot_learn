package com.demo.batch.db2db.batch;

import com.demo.batch.db2db.config.DataSourceHolder;
import com.demo.batch.db2db.entity.User;
import com.demo.batch.db2db.mapper.master.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserItemProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);


    @Autowired
    private UserDao userDao;

    @Override
    public User process(final User user) throws Exception {
        User transformedUser = getTransformedUser(user);
//        DataSourceHolder.set("master");
//        insert();
        return transformedUser;
    }

    private static User getTransformedUser(User user) {
        String name = user.getName().toUpperCase();
        int age = user.getAge() + 1;
        User transformedUser = new User();
        transformedUser.setAge(age);
        transformedUser.setName(name);
//        log.info("Converting (" + user + ") into (" + transformedUser + ")");
        return transformedUser;
    }

    private void insert() {
        User user1;
        for (int i = 0; i < 1; i++) {
            user1 = new User();
            user1.setName(UUID.randomUUID().toString().replace("-", ""));
            user1.setAge(i);
            userDao.insertUser(user1);
        }
        log.info("insert success!!!");
    }

}
