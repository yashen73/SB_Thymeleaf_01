package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import com.example.SB_Thymeleaf_01.Service.LoginService;
import com.example.SB_Thymeleaf_01.Service.RegisterCustomer;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private CustomerSerivce service;
    @Autowired
    private LoginService loginservice;
    @Autowired
    private RegisterCustomer registercustomer;

    public CustomerController(CustomerSerivce service){
        this.service = service;
    }

    @RequestMapping("/")
    public String index(){
        System.out.println("index page is calling...");
        return "index";
    }

    @RequestMapping("/CustomerSignup")
    public String CustomerSignup(){
        System.out.println("Customer Sign Up is loaded ...");
        return "CustomerSignup";
    }

    @PostMapping("/signup")
    public String save(@ModelAttribute Customer customer,Model model){
        System.out.println("sign up is called  . . . .");
        String regsitrationResult =registercustomer.register(customer);
        if(regsitrationResult == "Email already exists"){
            model.addAttribute("message", "This Email is already has been registered . . . ");
            System.out.println("rejected due to existing email . . . ");
            return "CustomerLogin";
        }else {
            model.addAttribute("message" ,"Registration is successful...");
            System.out.println("Registration Successfull .....");

            return "dashbaord";
        }
    }

    @RequestMapping("/CustomerLogin")
    public String CustomerLogin(){
        System.out.println("Customer login is loaded ...");
        return "CustomerLogin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String mail, @RequestParam String password, HttpSession session, Model model){
        String loginResult = loginservice.customerlogincheckup(mail, password);

        if (loginResult == "Login Successful"){

            System.out.println("Login is Successful & Customer "+mail+" is login....");
            session.setAttribute("loggedCustomer", mail);

            return "dashboard";

        }else if(loginResult == "Invalid login"){

            model.addAttribute("alertmessage", loginResult);

            return "CustomerLogin";

        }else{

            model.addAttribute("alertmessage", loginResult);

            return "CustomerLogin";
        }


    }

}
