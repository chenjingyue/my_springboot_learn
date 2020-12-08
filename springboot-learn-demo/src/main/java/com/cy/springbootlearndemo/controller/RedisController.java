package com.cy.springbootlearndemo.controller;

import com.cy.springbootlearndemo.model.ResponseVO;
import com.cy.springbootlearndemo.service.impl.RedisTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisController {
    private static final Logger logger = LogManager.getLogger(RedisController.class);

//    @Autowired
    private RedisTool redisTool;

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public ResponseVO<Map<String, Object>> pullCodeFromGitHub() {
        Map<String, Object> resultMap = new HashMap<>();
        ResponseVO<Map<String, Object>> responseVO = new ResponseVO<Map<String, Object>>();
        responseVO.setCode(200);
        responseVO.setMessage("success");
        responseVO.setData(resultMap);
        try {
            String lockKey = "ned";
            String requestId = "true";
            int expireTime = 600;
            boolean result = redisTool.tryGetDistributedLock(lockKey, requestId, expireTime);
            resultMap.put("result",result);
        } catch (Exception e) {
            logger.error("GitHubController.pullCodeFromGitHub error", e);
        }
        return responseVO;
    }
}
