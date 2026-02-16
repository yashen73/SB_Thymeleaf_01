package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

    private CustomerSerivce service;

    public CustomerController(CustomerSerivce service){
        this.service = service;
    }

    @RequestMapping("/")
    public String index(){
        System.out.println("index page is calling...");
        return "index";
    }

    @RequestMapping("/CustomerLogin")
        public String CustomerLogin(){
        System.out.println("Customer login is loaded ...");
            return "CustomerLogin";
        }
    @RequestMapping("/CustomerSignup")
    public String CustomerSignup(){
        System.out.println("Customer Sign Up is loaded ...");
        return "CustomerSignup";
    }

    @RequestMapping("/login")
    public String login(){
        return "dashboard";
    }

    @PostMapping("/signup")
    public String save(@ModelAttribute Customer customer){
        service.save(customer);
        return "dashboard";
    }
}
