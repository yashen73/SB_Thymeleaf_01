package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}
