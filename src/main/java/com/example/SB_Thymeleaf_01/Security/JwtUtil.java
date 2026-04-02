package com.example.SB_Thymeleaf_01.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private String secret = "mySecretKey";


    //This where Generate Token
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()

                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60)) // for 1 hour
                .signWith(key)
                .compact();
    }

    //Validate token
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
