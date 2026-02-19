package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
    Optional<Customer> findByMail(String mail);

    boolean existsByMail(String mail);
}
