package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Optional<Admin> findByadminusername(String adminusername);
}
