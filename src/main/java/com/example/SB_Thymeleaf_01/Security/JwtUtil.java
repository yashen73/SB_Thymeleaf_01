package com.example.SB_Thymeleaf_01.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String secret = "YashensDevelopingSecureSecretKeyForJwtAuthenticationSystem2026";
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    Map<String, Object> claims = new HashMap<>();

    //This where Generate Token
    public String generateToken(String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*3)) // for 1 hour
                .signWith(key)
                .compact();
    }

    //Validate token
    public String extractUsername(String authHeader) {
        String token = authHeader.substring(7);//removing Bearer first..

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
