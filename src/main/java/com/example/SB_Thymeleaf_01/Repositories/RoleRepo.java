package com.example.SB_Thymeleaf_01.Repositories;


import com.example.SB_Thymeleaf_01.Models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Long> {
}
