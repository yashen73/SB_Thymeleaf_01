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
                        .ignoringRequestMatchers("/chat-websocket**")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/chat-websocket/**")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                );


        return http.build();
    }
}
