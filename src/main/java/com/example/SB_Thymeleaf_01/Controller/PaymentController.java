package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.ItemPaymentRequest;
import com.example.SB_Thymeleaf_01.Service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-intent")
    public PaymentIntent createPayment(@RequestBody ItemPaymentRequest request, @RequestHeader("Authorization") String token) throws StripeException {

        return stripeService.createPaymentIntent(request, token);
    }
}