package com.example.jwtproject.security;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshExpirationTime;

    private static final String SECRET_KEY = "nC+ba4h5u+5gXo93wPL8uW1OHm2r8WvVd4d9aL3/A3FzqlN+wLj1TrXoGp7zFo+P\r\n"
    		+ ""; // 하드코딩된 비밀키

    @Bean
    public JwtProvider jwtProvider() {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new JwtProvider(secretKey, expirationTime, refreshExpirationTime);
    }
}
