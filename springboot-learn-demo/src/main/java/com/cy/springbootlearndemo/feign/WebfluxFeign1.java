package com.cy.springbootlearndemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-webflux1", fallback = WebfluxFeign1Fallback.class)
public interface WebfluxFeign1 {


    @GetMapping("/user")
    String user(@RequestParam(required = false, name = "name") String name);
}
