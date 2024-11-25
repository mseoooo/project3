package com.example.jwtproject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private final SecretKey secretKey;
    private final long expirationTime;
    private final long refreshExpirationTime;

    public JwtProvider(SecretKey secretKey, long expirationTime, long refreshExpirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public String generateToken(String name, String id) {
    	Claims claims = Jwts.claims();
        claims.put("name", name);  // name을 Claims에 추가
        claims.put("id", id);      // id를 Claims에 추가

        return Jwts.builder()
        		 .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public String generateRefreshToken(String name, String id) {
    	Claims claims = Jwts.claims();
        claims.put("name", name);  // name을 Claims에 추가
        claims.put("id", id);      // id를 Claims에 추가

        return Jwts.builder()
                .setClaims(claims)  // Claims에 설정된 name과 id를 포함
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 만료된 경우 예외를 던짐
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
    	 Claims claims = Jwts.parserBuilder()
                 .setSigningKey(secretKey)
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
         return claims.get("name", String.class);  // Claims에서 name을 추출
     
    }
    public String getIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", String.class);  // Claims에서 id를 추출
    }
    
}
