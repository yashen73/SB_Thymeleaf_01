package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Admin;
import com.example.SB_Thymeleaf_01.Repositories.AdminRepo;
import jakarta.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
public class AdminLoginService {
    @Autowired
    public AdminRepo adminRepo;


    public String AdminLoginCheckup(String username, String password) {
        Optional<Admin> admin = adminRepo.findByadminusername(username);
        System.out.println(admin);
        System.out.println("Admin Login service in AdminLoginService is called ...");
        if(matches(password, admin.get().getAdminPassword())){
            System.out.println("Admin Login credentials match and return Admin Dashbaord....");
            return "Successful";
        }else {
            System.out.println("Admin credentials are not valid.");
            return "invalid";

        }
    }
}
