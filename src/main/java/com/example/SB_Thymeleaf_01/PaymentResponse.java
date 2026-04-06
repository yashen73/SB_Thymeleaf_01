package com.example.SB_Thymeleaf_01;

import jakarta.persistence.Entity;

@Entity
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

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }
}
