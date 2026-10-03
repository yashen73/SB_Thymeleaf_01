package com.example.SB_Thymeleaf_01.Service;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Repositories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import javax.security.auth.login.CredentialException;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.regex.Pattern.matches;

@Service
public class AdminLoginService {
    @Autowired
    public AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String AdminLoginCheckup(Admin admin) {
        Optional<Admin> admin1 = adminRepo.findByadminusername(admin.getAdminusername());
        System.out.println(admin1);
        System.out.println("Admin Login service in AdminLoginService is called ...");
        try{
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
}
