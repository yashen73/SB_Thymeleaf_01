package com.example.SB_Thymeleaf_01.Config;

import com.example.SB_Thymeleaf_01.Components.JWTLoginHandler;
import com.example.SB_Thymeleaf_01.Components.JwtFilter;
import com.example.SB_Thymeleaf_01.Service.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

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
    @Order(1)
    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/auth/custsignup",
                                "/auth/custlogin",
                                "/auth/AdminSignUp",
                                "/auth/adminLoginCheckup",
                                "/adminChat/**",
                                "/cust/seeCSRF",
                                "/chat-websocket**",
                                "/item/addProductOnTrendingItems"
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/api",
                                "/admin/AdminLogin",
                                "/admin/AdminDashboard",
                                "/adminChat/chat/loadChatHeader",
                                "/favicon.ico",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/error"
                                )
                        .permitAll()
                        //Page Rendering
                        .requestMatchers(
                                "/index",
                                "/auth/AdminSignUp",
                                "/chat-websocket/**",
                                "/chat-websocket-native/**",
                                "/Profile",
                                "/CustomerLogin",
                                "/CustomerSignup",
                                "/signup",
                                "/AboutUs",
                                "/product/productdetail/{productid}",
                                "/Locations",
                                "/Branches",
                                "/Privacy_Policies"
                        )
                        .permitAll()
                        //For cart
                        .requestMatchers(
                                "/cart/Add_to_Cart",
                                "/cart/RemoveFromCart",
                                "/cart/ShowCartItems",
                                "/cart/Cart_Health"
                        )
                        .permitAll()

                        //Authorization
                        .requestMatchers(
                                "/auth/custlogin",
                                "/auth/custsignup",
                                "/auth/AdminSignUp",
                                "/auth/adminLoginCheckup"
                        )
                        .permitAll()
                        //Item
                        .requestMatchers(
                                "/item/addProductOnTrendingItems",
                                "/itemsforbrands",
                                "/item/showAllItems",
                                "/cust/g",
                                "/item/showAllItems",
                                "/item/updateAnItem",
                                "/item/deleteAnItem"
                        )
                        .permitAll()
                        //Payment Requests
                        .requestMatchers(
                                "/api/payment/checkout"
                        )
                        .permitAll()
                        //Payment Requests
                        .requestMatchers(
                                "/images/product_thumbnail_images/**",
                                "/images/product_detail_images/**"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}

/*
* .formLogin(form -> form
                        .loginPage("/admin/AdminLogin") ///admin/AdminLogin
                        .failureUrl("/admin/AdminLogin")
                        .usernameParameter("adminusername")
                        .passwordParameter("adminPassword")
                        .successHandler(jwtLoginHandler)
                        .permitAll()
                )*/