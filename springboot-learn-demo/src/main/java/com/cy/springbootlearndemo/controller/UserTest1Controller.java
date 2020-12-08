package com.cy.springbootlearndemo.controller;

import com.cy.springbootlearndemo.model.ResponseVO;
import com.cy.springbootlearndemo.model.User;
import com.cy.springbootlearndemo.service.test1.impl.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/test1")
//@CrossOrigin("*")
public class UserTest1Controller {
    private static final Logger logger = LogManager.getLogger(UserTest1Controller.class);

    @Resource(name="userServcieTest1")
    private UserService userService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value="/userstest1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> getAllUserTest1() {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            List<User> allUser = userService.selectAllUser();
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(allUser);
            logger.info("User info: " + allUser);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value="测试多数据源事务", notes="测试多数据源事务")
    @RequestMapping(value="/transactional", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> testTransactional() {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            userService.testTransactional();
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }




    @ApiOperation(value="更新用户列表", notes="更新用户列表")
    @RequestMapping(value="/user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVO<List<User>> updateUser(@RequestBody User user) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            userService.updateUser(user);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value="新增用户列表", notes="新增用户列表")
    @RequestMapping(value="/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO<List<User>> insertUser(@RequestBody User user) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            userService.insertUser(user);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }



}
