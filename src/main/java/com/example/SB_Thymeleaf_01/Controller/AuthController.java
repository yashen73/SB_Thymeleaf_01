package com.example.SB_Thymeleaf_01.Controller;


import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.AdminLoginService;
import com.example.SB_Thymeleaf_01.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
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

    @PostMapping("/AdminSignUp")
    public String  AdminSignUp(@ModelAttribute Admin admin){
        System.out.println("adminSignUp is called");
        try {
            String AdminSignUpResult = adminLoginService.AdminSignUp(admin);
            return AdminSignUpResult;
        }catch (Exception e){
            throw e;
        }
    }
}
