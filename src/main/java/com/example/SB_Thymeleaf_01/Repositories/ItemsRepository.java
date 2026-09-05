package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.Models.Availableitems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Availableitems, Long> {

    List<Availableitems> findAll();


}

