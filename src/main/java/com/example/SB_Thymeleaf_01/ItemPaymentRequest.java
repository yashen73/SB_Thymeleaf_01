package com.example.SB_Thymeleaf_01;

import jakarta.persistence.Entity;

@Entity
public class ItemPaymentRequest {

    public static class buyingitem{
        private Long productid;
        private int quantity;

        public Long getProductid() {
            return productid;
        }

        public void setProductid(Long productid) {
            this.productid = productid;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
