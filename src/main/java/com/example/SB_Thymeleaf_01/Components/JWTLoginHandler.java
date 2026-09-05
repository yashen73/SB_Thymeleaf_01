package com.example.SB_Thymeleaf_01.Components;

import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setHttpOnly(true);    //No Java script access
        jwtCookie.setSecure(true);      //Only send over HTTPS
        jwtCookie.setPath("/admin/AdminDashboard");
        jwtCookie.setMaxAge(24*60*60);  //24 hours
        jwtCookie.setAttribute("SameSite", "Strict");
        response.addCookie(jwtCookie);

        //send token response header for JAVASCRIPT to read
        response.setHeader("jwt", jwtToken);

        //setting where response should go
        response.sendRedirect("/admin/AdminDashboard");

        //store in session for quick access
        request.getSession().setAttribute("JWT_TOKEN", jwtToken);

        System.out.println("JWT token Generated for : " + authentication.getName());
        System.out.println("TOKEN :" + jwtToken);

        //Redirect to success URL . . . .
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
