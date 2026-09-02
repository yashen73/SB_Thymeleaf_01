package com.example.SB_Thymeleaf_01.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/login", "/chat-websocket**")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index",
                                "/auth/login",
                                "/chat-websocket/**",
                                "/chat-websocket-native/**",
                                "/Profile",
                                "/CustomerLogin",
                                "/signup",
                                "/AboutUs",
                                "/product/productdetail/{productid}",
                                "/itemsforbrands",
                                "/item/showAllItems",
                                "/api/payment/checkout",
                                "/cust/ShowAllCustomers",
                                "/api/",
                                "/favicon.ico",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                                )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}
