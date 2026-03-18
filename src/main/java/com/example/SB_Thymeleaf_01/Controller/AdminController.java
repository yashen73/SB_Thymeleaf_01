package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Service.AdminLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public AdminLoginService adminLoginService;

    @GetMapping("/AdminLogin")
    public String AdminLogin(){
        System.out.println("AdminLogin Page is directly called.");
        return "AdminLogin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String adminusername, @RequestParam String adminpassword, HttpSession session, RedirectAttributes redirectAttributes){
        System.out.println("Admin login method in AdminController Class is called....");
        String adminLoginResult = adminLoginService.AdminLoginCheckup(adminusername, adminpassword);

        if(adminLoginResult=="Successful"){
            return "Admindash";
        }else {
            redirectAttributes.addFlashAttribute("LoginFailureMessage", "Invalid");
            return "redirect:/AdminLogin";
        }

    }

    @GetMapping("/addProductOnTrendingItems")
    public String addProductOnTrendingItems(@ModelAttribute Availableitems availbleItems) {
        System.out.println(availbleItems);
        return
    }
}
