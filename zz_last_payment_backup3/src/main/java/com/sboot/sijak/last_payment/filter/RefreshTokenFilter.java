package com.sboot.sijak.last_payment.filter;

import com.sboot.sijak.last_payment.jwtutil.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RefreshTokenFilter implements Filter {

	private final JwtUtil jwtUtil;

	public RefreshTokenFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 필터 초기화 로직 (필요시)
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// 리프레시 토큰을 쿠키에서 가져오기
		Cookie[] cookies = httpRequest.getCookies();
		String refreshToken = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
					break;
				}
			}
		}

		// 필터 체인을 계속 진행
		chain.doFilter(request, response);
	}

}
