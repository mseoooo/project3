package com.example.jwtproject.controller;

import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtproject.dto.LoginRequest;
import com.example.jwtproject.dto.LoginResponse;
import com.example.jwtproject.dto.UserDto;
import com.example.jwtproject.model.User;
import com.example.jwtproject.security.JwtProvider;
import com.example.jwtproject.service.AuthService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:8810", allowCredentials = "true")
@RestController
@RequestMapping("/jwt")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }
   
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, User user, HttpServletResponse response) {
        // 로그인 인증 수행
        System.out.println("name: " + loginRequest.getId()); 

        // 토큰 생성
        String token = authService.authenticate(loginRequest);  // JWT 토큰
        String refreshToken = authService.generateRefreshToken(loginRequest.getId(), user.getName());  // Refresh 토큰
        // 토큰들을 응답에 포함
        return ResponseEntity.ok(new LoginResponse(token, refreshToken));  // 두 개의 토큰 반환
    }
    
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response,UserDto userdto) {
        // 쿠키에서 리프레시 토큰을 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        // 리프레시 토큰이 유효한 경우 새로운 JWT 발급
        String name = jwtProvider.getUsernameFromToken(refreshToken);
        String id = jwtProvider.getIdFromToken(refreshToken);
        String newToken = jwtProvider.generateToken(name,id);

        return ResponseEntity.ok(new LoginResponse(newToken));
    }
    

    
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            System.out.println("잘못된 토큰 형식: " + bearerToken);
            return ResponseEntity.badRequest().body("Invalid token format");
        }

        String token = bearerToken.substring(7); // "Bearer " 부분을 제거
        try {
            // 토큰 유효성 확인
            if (jwtProvider.validateToken(token)) {
                String Id = jwtProvider.getUsernameFromToken(token);
                System.out.println("유효한 토큰 - 사용자 이름: " + Id);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Token is valid");
                response.put("name", Id);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
        }
    }
}
