package com.example.my_springboot_learn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.my_springboot_learn.model.QueryCondition;
import com.example.my_springboot_learn.model.ResponseVO;
import com.example.my_springboot_learn.model.User;
import com.example.my_springboot_learn.service.impl.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<List<User>> getAllUser() {
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

    @ApiOperation(value="获取用户通过ID", notes="获取用户通过ID")
    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<User> getUserById(@PathVariable("id") int id) {
        ResponseVO<User> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            User user = userService.selectUserById(id);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(user);
            logger.info("User info: " + user);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value="修改用户通过ID", notes="修改用户通过ID")
    @RequestMapping(value="/user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseVO<User> updateUserById(@RequestBody User user) {
        ResponseVO<User> responseVO = new ResponseVO<>();
        try {
            logger.info("Query user information.");
            userService.updateUserById(user);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(user);
            logger.info("User info: " + user);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

    @ApiOperation(value="新增用户", notes="新增用户")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO<Void> insertUser(@RequestBody User user) {
        ResponseVO<Void> responseVO = new ResponseVO<>();
        try {
            logger.info("Request parameter: " + user);
            logger.info("Insert user information");
            userService.insertUser(user);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to insert user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }

   /* @RequestMapping(value = "/insert_param", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO<Void> insertUser(String param, HttpServletResponse response) {
        ResponseVO<Void> responseVO = new ResponseVO<>();
        try {
            logger.info("Request parameter: " + param);
            logger.info("Insert user information");
            JSONObject jsonObject = JSON.parseObject(param);
            User user = new User();
            user.setId(jsonObject.getInteger("id"));
            user.setUsername(jsonObject.getString("username"));
            user.setPassword(jsonObject.getString("password"));
            userService.insertUser(user);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
        } catch (Exception e) {
            logger.error("Failed to insert user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }
        return responseVO;
    }*/

    @ApiOperation(value="分页查询", notes="分页查询")
    @RequestMapping(value = "/user/limit", method = RequestMethod.POST)
    public ResponseVO<List<User>> queryUser(@RequestBody QueryCondition queryCondition) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {
            logger.info("Request parameter: " + queryCondition);
            logger.info("Paging query user information");
            List<User> users = userService.queryByCondition(queryCondition);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(users);
            logger.info("User info: " + users);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }

        return responseVO;
    }

   /* @ApiOperation(value="分页查询", notes="分页查询")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseVO<List<User>> queryUser1(@PathVariable("page") int page,@PathVariable("pageSize") int pageSize) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {

            logger.info("Request parameter page: " + page + "pageSize: " + pageSize);
            logger.info("Paging query user information");
            List<User> users = userService.queryByLimit(page, pageSize);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(users);
            logger.info("User info: " + users);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }

        return responseVO;
    }*/


    @ApiOperation(value="带条件分页查询", notes="带条件分页查询")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseVO<List<User>> queryUser2(QueryCondition queryCondition) {
        ResponseVO<List<User>> responseVO = new ResponseVO<>();
        try {

            logger.info("Request parameter: " + queryCondition);
            logger.info("Paging query user information");
            List<User> users = userService.queryByCondition(queryCondition);
            responseVO.setCode(HttpServletResponse.SC_OK);
            responseVO.setMessage("success");
            responseVO.setData(users);
            logger.info("User info: " + users);
        } catch (Exception e) {
            logger.error("Failed to query user information", e);
            responseVO.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseVO.setMessage("error");
        }

        return responseVO;
    }
}
