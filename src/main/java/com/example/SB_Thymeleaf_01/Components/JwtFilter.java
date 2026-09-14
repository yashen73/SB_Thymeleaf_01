package com.example.SB_Thymeleaf_01.Components;

import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autharizationHeader =  request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if(autharizationHeader != null && autharizationHeader.startsWith("Bearer ")) {
            userName = jwtUtil.extractUsername(autharizationHeader);
        }
    }
}
