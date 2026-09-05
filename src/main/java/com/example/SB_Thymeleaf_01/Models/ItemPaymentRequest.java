package com.example.SB_Thymeleaf_01.Models;

import lombok.Data;

@Data
public class ItemPaymentRequest {

        private Long productid;
        private int quantity;
        private Long amount;


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

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "buyingitem{" +
                    "productid=" + productid +
                    ", quantity=" + quantity +
                    ", amount=" + amount +
                    '}';

    }

}
