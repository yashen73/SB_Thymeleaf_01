package com.example.SB_Thymeleaf_01.Repositories;


import com.example.SB_Thymeleaf_01.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
}
