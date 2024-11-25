package com.example.jwtproject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtproject.dto.LoginRequest;
import com.example.jwtproject.mapper.UserMapper;
import com.example.jwtproject.model.User;
import com.example.jwtproject.security.JwtProvider;

@Service
public class AuthService {
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserMapper userMapper, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getId());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtProvider.generateToken(user.getId(),user.getName()); // Access Token 반환
    }

    // 리프레시 토큰 생성 메서드 추가
    public String generateRefreshToken(String name, String id) {
        return jwtProvider.generateRefreshToken(name,id);
    }
}
