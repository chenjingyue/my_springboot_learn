package com.cy.springbootlearndemo.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class WebfluxFeignFallback implements WebfluxFeign {
    @Override
    public String user(@RequestParam(required = false, name = "name") String name) {
        System.out.println("WebfluxFeignFallback: 调用失败");
        return null;
    }
}
