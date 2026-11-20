package com.example.SB_Thymeleaf_01.Security;

import com.example.SB_Thymeleaf_01.Models.Customer;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
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

        return "access_token_" + Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000L * 60 * 60 * 24 * 2)) // for 1 hour
                .signWith(key)
                .compact();
    }

    //Validate token
    public String extractUsername(String authHeader) {
        String token = authHeader.substring(13);//removing Bearer first..

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date tokenExpirationDate = extractAllClaims(token).getExpiration();

        return tokenExpirationDate.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try{
            String username = extractUsername(token);

            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


}
