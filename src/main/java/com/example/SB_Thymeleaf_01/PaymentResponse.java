package com.example.SB_Thymeleaf_01;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private String clientSecret;
    private Long orderID;

    public PaymentResponse(String clientSecret, Long orderID) {
        this.clientSecret = clientSecret;
        this.orderID = orderID;
    }


    public String getClientSecret() {
        return clientSecret;
    }

    public Long getOrderID() {
        return orderID;
    }
}
