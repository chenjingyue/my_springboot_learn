package com.example.my_springboot_learn.service;

import com.example.my_springboot_learn.mapper.UserMapper;
import com.example.my_springboot_learn.model.User;
import com.example.my_springboot_learn.service.impl.UserService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserServiceTest.class})

public class UserServiceTest {
    UserService userService;
    UserMapper userMapper;
    @Before
    public void setUp() throws Exception {
        userService = new UserService();
        userMapper = PowerMock.createMock(UserMapper.class);
        Whitebox.setInternalState(userService, "userMapper", userMapper);

    }

    @Test
    public void test_selectAllUser() throws Exception {
        List<User> list = new ArrayList<>();
        EasyMock.expect(userMapper.selectAllUser()).andReturn(list).anyTimes();

        PowerMock.replayAll();
        userService.selectAllUser();
        PowerMock.verifyAll();
    }



}
