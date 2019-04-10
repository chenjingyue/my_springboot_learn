package com.example.my_springboot_learn.controller;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@CrossOrigin("*")
public class FileController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/file/download", method = RequestMethod.GET)
    public String downloadFile(HttpServletResponse response) {
        String filePath = "C:\\Users\\LittleXianGirl\\Desktop\\aaa\\u=847327332,4076712088&fm=26&gp=0.jpg";
        File file = new File(filePath);
        String fileName = file.getName();
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("中文.png", "UTF-8"));
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return "success";
    }
}
