package com.example.SB_Thymeleaf_01.Controller.Payment;

import com.example.SB_Thymeleaf_01.Models.ItemPaymentRequest;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private final StripeService stripeService;
    private JwtUtil jwtUtil;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody ItemPaymentRequest request) throws StripeException {
        System.out.println(request+" is taken ::::: Payment Controller");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String url = stripeService.createCheckoutSession(request);
        return ResponseEntity.ok(url);
    }
}