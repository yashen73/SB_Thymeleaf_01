package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Cart;
import com.example.SB_Thymeleaf_01.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.cart = :cart")
    List<CartItem> findByCart(@Param("cart") Cart cart);
}