package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Models.Cart;
import com.example.SB_Thymeleaf_01.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.cart = :cart")
    List<CartItem> findByCart(@Param("cart") Cart cart);
}