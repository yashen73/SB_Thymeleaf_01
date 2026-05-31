package com.example.SB_Thymeleaf_01.Repositories;


import com.example.SB_Thymeleaf_01.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.customer.id=?1")
    Optional<Cart> findByCustomerId(Long user_id);
}
