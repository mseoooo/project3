package com.sboot.kaja;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sboot.kaja.dao")
public class HelloBootBatis01Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloBootBatis01Application.class, args);
	}

}
