package com.cy.springbootlearndemo;

import com.cy.springbootlearndemo.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@MapperScan(basePackages = "com.cy.springbootlearndemo.mapper", markerInterface = BaseMapper.class)
@EnableSwagger2
public class SpringbootLearnDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLearnDemoApplication.class, args);

//		AnnotationConfigApplicationContext ac =
//				new AnnotationConfigApplicationContext(MySpringbootLearnApplication.class);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
//		tomcatServletWebServerFactory.setPort(8989);
//		return tomcatServletWebServerFactory;
//	}

}
