package com.example.SB_Thymeleaf_01.Components;

import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import com.example.SB_Thymeleaf_01.Service.NewUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private NewUserDetailsService newUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        //catch incoming cookies..
        Cookie[] cookies = request.getCookies();
        String autherizationJWT = null;

        if(cookies != null) {
            for (Cookie cookie: cookies) {
                if("access_token".equals(cookie.getName())) {
                    autherizationJWT = cookie.getValue();
                    break;
                }
            }
        }


        if(autherizationJWT != null) {
            try{
                String username = jwtUtil.extractUsername(autherizationJWT);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = newUserDetailsService.loadUserByUsername(username);

                        if(jwtUtil.validateToken(autherizationJWT, userDetails)) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            System.out.println("set authentication for " + userDetails.getUsername());
                    }

                }
            }catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
            }
                    }
        // 4. Continue to the next filter
        filterChain.doFilter(request, response);
    }
}
