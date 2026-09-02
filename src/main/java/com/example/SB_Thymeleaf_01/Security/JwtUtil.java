package com.example.SB_Thymeleaf_01.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "YashensDevelopingSecureSecretKeyForJwtAuthenticationSystem2026";
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    //This where Generate Token
    public String generateToken(String username) {


        return Jwts.builder()

                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24)) // for 1 hour
                .signWith(key)
                .compact();
    }

    //Validate token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
