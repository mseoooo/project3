
package com.sboot.sijak.sijak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//spring security의 기본 암호화 형식
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WSecurityConfig extends WebSecurityConfigurerAdapter {
	protected void configure(HttpSecurity hs1) throws Exception {
		hs1.csrf(csrf -> csrf.disable()).logout(logout -> logout.logoutUrl("/cc").invalidateHttpSession(true)
				.deleteCookies("jwtToken", "refreshToken"));

	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder amb1) throws Exception {

		amb1.inMemoryAuthentication().withUser("aaaa").password(passwordEncoder().encode("aaaa")).roles("ADMIN");
		amb1.inMemoryAuthentication().withUser("*").password(passwordEncoder().encode("*")).roles("USER");
	}

	@Bean // 자바스타일로 정의
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}// passwordEncoder() -end
}// WSecurityConfig-class-end
