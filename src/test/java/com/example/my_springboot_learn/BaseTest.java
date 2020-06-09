package com.example.my_springboot_learn;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public abstract class BaseTest {

    @Before
    public void before() {
        log.info("启动测试...............");
    }

    @After
    public void after() {
        log.info("结束测试................");
    }
}