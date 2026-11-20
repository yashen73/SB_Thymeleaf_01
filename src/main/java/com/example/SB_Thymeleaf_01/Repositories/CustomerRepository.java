package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long>{
    Optional<Customer> findByMail(String mail);
    boolean existsByMail(String mail);


    @Query("SELECT c FROM Customer c WHERE c.username = :username AND c.isActive = true")
    Optional<Customer> findByUsername(@Param("username") String username);
}
