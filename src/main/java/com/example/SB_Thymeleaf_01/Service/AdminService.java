package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Models.Roles;
import com.example.SB_Thymeleaf_01.Repositories.AdminRepo;
import com.example.SB_Thymeleaf_01.Repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Role;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    public AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;


    public String AdminLoginCheckup(Admin admin) {
        Optional<Admin> admin1 = adminRepo.findByadminUsername(admin.getAdminUsername());
        System.out.println(admin1);
        System.out.println("Admin Login service in AdminLoginService is called ...");
        try{
            if(admin1.isEmpty()) {
                System.out.println("admin doesn't exist using this username....");
            }

            if(passwordEncoder.matches(admin.getAdminPassword(), admin1.get().getAdminPassword())){
                System.out.println("Admin Login credentials match and return Admin Dashbaord....");
                return "Successful";
            }else {
                System.out.println("Admin credentials are not valid.");
                return "Invalid Credentials";
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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

    public String AddingRole(@ModelAttribute Roles role) {
        roleRepo.save(role);
        return "Success";
    }

    public  Admin findAdminByUsername(String username) {
        Optional<Admin> admin = adminRepo.findByadminUsername(username);
        if(admin.isPresent()) {
            return admin.get();
        }else {
            return null;
        }
    }
}
