package com.example.SB_Thymeleaf_01.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long cart_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public Customer customer;

    @Column(name="created_id", nullable = false, updatable = false)
    public LocalDateTime created_at;

    @PrePersist
    public void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id=" + cart_id +
                ", customer=" + customer +
                ", created_at=" + created_at +
                '}';
    }
}
