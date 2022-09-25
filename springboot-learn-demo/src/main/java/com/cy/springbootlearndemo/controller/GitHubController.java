package com.cy.springbootlearndemo.controller;

import com.cy.springbootlearndemo.model.ResponseVO;
import com.cy.springbootlearndemo.service.IGitHubService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class GitHubController {
    private static final Logger logger = LogManager.getLogger(GitHubController.class);

    @Autowired
    private IGitHubService gitHubService;

    @RequestMapping(value = "/code/pull", method = RequestMethod.GET)
    public ResponseVO<Map<String, Object>> pullCodeFromGitHub() {
        Map<String, Object> resultMap = new HashMap<>();
        ResponseVO<Map<String, Object>> responseVO = new ResponseVO<Map<String, Object>>();
        responseVO.setCode(200);
        responseVO.setMessage("success");
        responseVO.setData(resultMap);
        try {
            String result = gitHubService.pullCodeFromGitHub();
            resultMap.put("result",result);
        } catch (Exception e) {
            logger.error("GitHubController.pullCodeFromGitHub error", e);
        }
        return responseVO;
    }

}
