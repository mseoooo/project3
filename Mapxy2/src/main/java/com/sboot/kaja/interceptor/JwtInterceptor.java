package com.sboot.kaja.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sboot.kaja.jwtutil.JwtUtil;



@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil; // JWT 유효성 검사하는 JwtUtil 주입

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String jwtToken = getJwtTokenFromCookies(request);

		if (jwtToken == null || jwtToken.isEmpty()) {
			// JWT 토큰이 없는 경우

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script type='text/javascript'>");
			out.println("alert('로그인이 필요합니다.');"); // 알림 창 표시
			out.println("window.location.href = '/';"); // 리다이렉트
			out.println("</script>");
			return false; // 요청을 차단하고 리다이렉션

		}

		// JWT 토큰에서 사용자 ID를 추출 및 유효성 검사
		String userId = jwtUtil.getUserIdFromToken(jwtToken);
		String userName = jwtUtil.getUserNameFromToken(jwtToken);
		System.out.println("babababa" + userId);
		if (userId == null) {
			// 토큰이 유효하지 않으면 로그인 페이지로 리디렉션
			response.sendRedirect("http://localhost:8030/jwt/refresh");

			return false; // 요청을 차단
		}

		// 토큰이 유효하면 세션에 사용자 정보를 저장
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("userName", userName);
		return true; // 요청이 유효하면 계속 진행
	}

	// 요청에서 JWT 토큰을 추출하는 메서드
	private String getJwtTokenFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals(cookie.getName())) {
					return cookie.getValue(); // 쿠키에서 JWT 토큰 값을 반환
				}
			}
		}

		return null; // 쿠키에 JWT 토큰이 없는 경우
	}
}
