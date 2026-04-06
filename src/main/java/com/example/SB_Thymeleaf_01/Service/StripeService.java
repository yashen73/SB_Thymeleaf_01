package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.ItemPaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public PaymentIntent createPaymentIntent(ItemPaymentRequest request, String token) throws StripeException {

        PaymentIntentCreateParams para = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();
        return PaymentIntent.create(para);
    }
}
