package com.example.SB_Thymeleaf_01.Controller;


import com.example.SB_Thymeleaf_01.Exceptions.DuplicateEmailException;
import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.AdminLoginService;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import com.example.SB_Thymeleaf_01.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private CustomerSerivce customerSerivce;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/custlogin")
    public String login(@RequestBody Customer customer) {

        System.out.println();
        System.out.println();
        System.out.println("Auth is called");

        String loginresult = loginService.customerlogincheckup(customer.getMail(), customer.getPassword());

        if (loginresult.equals("Login Successful")) {
            System.out.println("Customer Login is successful...");
            String token = jwtUtil.generateToken(customer.getMail());
            System.out.println("The Token is:"+token);
             return token;
        } else if (loginresult.equals("user not found")) {
            throw new RuntimeException("user not Found please sign in first.");
        }else {
            throw new RuntimeException("invalid Credentials");
        }
    }

    @PostMapping("/custsignup")
    public String custsignup(@RequestBody Customer customer){
        System.out.println("Cust Sign up is calling : "+ customer);
        try{
            String encodedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(encodedPassword);
            customerSerivce.save(customer);
            return "success";
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/AdminSignUp")
    public String  AdminSignUp(@RequestBody Admin admin){
        System.out.println("adminSignUp is called");
        try {
            String adminEncodedPassword = passwordEncoder.encode(admin.getAdminPassword());
            admin.setAdminPassword(adminEncodedPassword);
            String AdminSignUpResult = adminLoginService.AdminSignUp(admin);
            return AdminSignUpResult;
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/adminLoginCheckup")
    public String adminLoginCheckup(@RequestBody Admin admin){
        System.out.println("Admin Loging Checkup is called ....");
        String loginCheckUpResult =  adminLoginService.AdminLoginCheckup(admin);
        if(loginCheckUpResult.equals("Successful")){
            String token = jwtUtil.generateToken(admin.getAdminusername());
            return token;
        } else if (loginCheckUpResult.equals("Invalid Credentials")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
