package com.sboot.sijak.jwtutil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "nC+ba4h5u+5gXo93wPL8uW1OHm2r8WvVd4d9aL3/A3FzqlN+wLj1TrXoGp7zFo+P\r\n"
			+ ""; // 실제 비밀키로 교체해야 합니다.

	// JWT 토큰에서 사용자 ID 추출
	public static String getUserNameFromToken(String token) {
		try {
			Claims claims = Jwts.parser()

					.setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)) // 서명 검증
					.parseClaimsJws(token).getBody();
			// "sub" 클레임에서 사용자 ID 추출
			return claims.get("id", String.class); // 사용자 이름이나 사용자 ID가 'sub'에 저장된 경우
		} catch (ExpiredJwtException e) {
			// 만료된 토큰인 경우 예외 처리
			System.out.println("Expired token: " + e.getMessage());
		} catch (SignatureException e) {
			// 서명이 유효하지 않은 경우 예외 처리
			System.out.println("Invalid token signature: " + e.getMessage());
		} catch (Exception e) {
			// 그 외의 예외 처리
			System.out.println("Error parsing JWT: " + e.getMessage());
		}
		return null; // 유효하지 않거나 오류가 발생한 경우
	}

	public static String getUserIdFromToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)) // 서명 검증
					.parseClaimsJws(token).getBody();

			return claims.get("name", String.class); // Claims에서 "name" 값을 추출
		} catch (ExpiredJwtException e) {
			System.out.println("Expired token: " + e.getMessage());
		} catch (SignatureException e) {
			System.out.println("Invalid token signature: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error parsing JWT: " + e.getMessage());
		}
		return null; // 유효하지 않거나 오류가 발생한 경우
	}

	// JWT 토큰 검증
	public static boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)) // 서명 검증
					.parseClaimsJws(token); // 토큰을 파싱하여 유효성 검사

			return true; // 유효한 토큰
		} catch (ExpiredJwtException e) {
			System.out.println("Expired token: " + e.getMessage());
		} catch (SignatureException e) {
			System.out.println("Invalid token signature: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Invalid JWT: " + e.getMessage());
		}
		return false; // 유효하지 않은 토큰
	}
}
