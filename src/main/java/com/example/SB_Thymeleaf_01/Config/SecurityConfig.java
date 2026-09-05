package com.example.SB_Thymeleaf_01.Config;

import com.example.SB_Thymeleaf_01.Components.JWTLoginHandler;
import com.example.SB_Thymeleaf_01.Service.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTLoginHandler jwtLoginHandler;

    private final AdminUserDetailsService adminUserDetailsService;

    public SecurityConfig(AdminUserDetailsService adminUserDetailsService) {
        this.adminUserDetailsService = adminUserDetailsService;
    }

    @Bean
    public  DaoAuthenticationProvider authenticationProvider(AdminUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

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
                                "/auth/AdminSignUp",
                                "/chat-websocket/**",
                                "/chat-websocket-native/**",
                                "/Profile",
                                "/CustomerLogin",
                                "/signup",
                                "/AboutUs",
                                "/cust/seeCSRF",
                                "/product/productdetail/{productid}",
                                "/itemsforbrands",
                                "/item/showAllItems",
                                "/api/payment/checkout",
                                "/cust/ShowAllCustomers",
                                "/api/payment/checkout",
                                "/item/addProductOnTrendingItems",
                                "/item/showAllItems",
                                "/item/updateAnItem",
                                "/item/deleteAnItem",
                                "/api",
                                "/favicon.ico",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                                )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/AdminLogin") ///admin/AdminLogin
                        .failureUrl("/admin/AdminLogin")
                        .usernameParameter("adminusername")
                        .passwordParameter("adminPassword")
                        .successHandler(jwtLoginHandler)
                        .permitAll()
                );



        return http.build();
    }
}