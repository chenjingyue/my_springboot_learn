package com.cy.springbootlearndemo.service.impl;

import com.cy.springbootlearndemo.service.IGitHubService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService implements IGitHubService {
    // 代码拉去到固定目录下
    String codePath = " G:\\work\\TempCode\\my_springboot_learn";
    String codePath1 = " G:\\work\\TempCode\\redis";
    String gitPath = "git@github.com:chenjingyue/my_springboot_learn.git";
    String gitPath1 = " git@github.com:MicrosoftArchive/redis.git";
    @Override
    public String pullCodeFromGitHub() {
        String cmdStr = "cmd /k start git clone "+gitPath1+codePath1;
//        String cmdStr1 = "";
        List<String> list = new ArrayList<>();
        Runtime run = Runtime.getRuntime();
//        InputStream in = null;
        try {

            System.out.println("zhixing start ");
            Process process =  run.exec(cmdStr);
            System.out.println("zhixing wancheng ");
            int exitVal = process.waitFor();
            System.out.println("Exited with error code " +exitVal);

//            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
//
//            String line = null;
//
//            while ((line = input.readLine()) != null) {
//                System.out.println(line);
//            }



//            in = process.getInputStream();
//            list = IOUtils.readLines(in);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            IOUtils.closeQuietly(in);
        }
        return list.toString();
    }
}
