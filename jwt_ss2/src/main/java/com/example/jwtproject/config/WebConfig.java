package com.example.jwtproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080", "http://localhost:8810", "http://localhost:8820",
						"http://localhost:8088") // 허용할 프론트엔드 서버 주소
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowCredentials(true) // 쿠키가 포함된 요청을 허용
				.maxAge(3600); // 캐시 시간 설정
	}

}
