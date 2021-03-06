package com.cy.springbootlearndemo.controller;

import com.cy.springbootlearndemo.feign.WebfluxFeign;
import com.cy.springbootlearndemo.feign.WebfluxFeign1;
import com.cy.springbootlearndemo.model.ResponseVO;
import com.cy.springbootlearndemo.model.User;
import com.cy.springbootlearndemo.service.impl.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/test2")
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WebfluxFeign webfluxFeign;

    @Autowired
    private WebfluxFeign1 webfluxFeign1;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> getAllUser() {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            System.out.println("webfluxFeign: "+webfluxFeign.user("aaa"));
            System.out.println("webfluxFeign1: "+webfluxFeign1.user("aaa"));
//            List<User> allUser = userService.selectAllUser();
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
//            responseVO.setData(allUser);
//            logger.info("User info: " + allUser);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value = "更新用户列表", notes = "更新用户列表")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
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

    @ApiOperation(value = "新增用户列表", notes = "新增用户列表")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
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


    @ApiOperation(value = "新增用户列表", notes = "新增用户列表")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> hello(@RequestParam String name) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            String url = "http://service-hi/hi?name=" + name;
            String forEntity = restTemplate.getForObject(url, String.class);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }


    @ApiOperation(value = "testCustomerFilter", notes = "testCustomerFilter")
    @RequestMapping(value = "/testCustomerFilter", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> testCustomerFilter() {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("testCustomerFilter");
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value = "forwardTest", notes = "forwardTest")
    @RequestMapping(value = "/forwardTest", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> forwardTest() {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("forwardTest");
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
