package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Repositories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
public class AdminLoginService {
    @Autowired
    public AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public String AdminSignUp(@ModelAttribute Admin admin){
        String encodedPassword = passwordEncoder.encode(admin.getAdminPassword());
        try {
            admin.setAdminPassword(encodedPassword);
            adminRepo.save(admin);
            return "Successfull";
        }catch (Exception e){
            throw e;
        }
    }
}
