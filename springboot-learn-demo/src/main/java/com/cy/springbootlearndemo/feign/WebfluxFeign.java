package com.cy.springbootlearndemo.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "spring-webflux",fallback = WebfluxFeignFallback.class)
public interface WebfluxFeign {


    @GetMapping("/user")
    String user(@RequestParam(required = false,name = "name") String name);
}
