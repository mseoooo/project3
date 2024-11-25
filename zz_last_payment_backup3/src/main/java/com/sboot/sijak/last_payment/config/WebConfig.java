package com.sboot.sijak.last_payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sboot.sijak.last_payment.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	 @Autowired
	    private JwtInterceptor jwtInterceptor;
	 
	 @Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**") // 모든 요청에 대해 CORS 허용
					.allowedOrigins("http://localhost:8030", "http://localhost:8080","http://localhost:8088", "http://localhost:8810","http://localhost:8761") // 허용할 출처 설정
																							// 프론트엔드)
					.allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
					.allowCredentials(true) // 쿠키와 인증 정보를 포함한 요청을 허용
					.maxAge(3600); // 1시간 동안 CORS 정책을 캐시
		}
	 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JWT 인터셉터 설정: 모든 요청에 대해 JWT 토큰을 검증
        registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**")  // 모든 경로에 대해 인터셉터 적용
               .excludePathPatterns("/shop","/js/**","/css/**", "/image/**");  // 로그인, 회원가입, 공개된 경로 제외
      
    }
    
	
}
