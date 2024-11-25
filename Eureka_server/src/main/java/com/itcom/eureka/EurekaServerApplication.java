package com.itcom.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication 
// @SpringBootConfiguration(bean 객체 생성) 
// + EnableAutoConfiguration(bean 객체 활성화) ==> web 선택하면 자동으로 관계된 내용 구성
// + ComponenetScan(@service, @Repository ...)
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
