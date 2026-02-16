package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
}
