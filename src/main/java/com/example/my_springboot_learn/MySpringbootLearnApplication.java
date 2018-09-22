package com.example.my_springboot_learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.my_springboot_learn.mapper")
public class MySpringbootLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringbootLearnApplication.class, args);
	}
}
