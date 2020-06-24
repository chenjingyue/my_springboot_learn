package com.example.my_springboot_learn;

import com.example.my_springboot_learn.mapper.BaseMapper;
import com.example.my_springboot_learn.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@MapperScan(basePackages = "com.example.my_springboot_learn.mapper", markerInterface = BaseMapper.class)
@EnableSwagger2
public class MySpringbootLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringbootLearnApplication.class, args);

//		AnnotationConfigApplicationContext ac =
//				new AnnotationConfigApplicationContext(MySpringbootLearnApplication.class);


	}
}
