package com.cy.springbootlearndemo.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class WebfluxFeign1Fallback implements WebfluxFeign1 {
    @Override
    public String user(@RequestParam(required = false, name = "name") String name) {
        System.out.println("WebfluxFeignFallback1: 调用失败");
        return null;
    }
}
