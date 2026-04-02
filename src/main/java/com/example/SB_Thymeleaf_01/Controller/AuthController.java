package com.example.SB_Thymeleaf_01.Controller;


import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody Customer customer) {

        System.out.println("Auth is called");

        String loginresult = loginService.customerlogincheckup(customer.getMail(), customer.getPassword());

        if (loginresult.equals("Login Successful")) {
            System.out.println("Customer Login is successful...");
            String token = jwtUtil.generateToken(customer.getMail());

             return token;
        } else if (loginresult.equals("user not found")) {
            throw new RuntimeException("user not Found please sign in first.");
        }else {
            throw new RuntimeException("invalid Credentials");
        }
    }
}
