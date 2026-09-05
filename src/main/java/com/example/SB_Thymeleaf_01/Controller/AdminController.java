package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Config.Password_Configuration;
import com.example.SB_Thymeleaf_01.Service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public AdminLoginService adminLoginService;
    @Autowired
    private Password_Configuration passwordEncoder;

    @GetMapping("/AdminLogin")
    public String AdminLogin(){
        System.out.println("AdminLogin Page is directly called.");
        return "AdminLogin";
    }

    @GetMapping("/AdminDashboard")
    public String AdmiDashboard() {
        return "Admindash";
    }

}
