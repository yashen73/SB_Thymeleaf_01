package com.example.SB_Thymeleaf_01.Controller.Auth;
import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Models.Roles;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.AdminService;
import com.example.SB_Thymeleaf_01.Service.CartService;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import com.example.SB_Thymeleaf_01.Service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Role;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerSerivce customerSerivce;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartService cartService;

    @PostMapping("/custlogin")
    public ResponseEntity<?> login(@RequestBody Customer customer) {

        System.out.println();
        System.out.println();
        System.out.println("Auth is called");

        String loginresult = loginService.customerlogincheckup(customer.getMail(), customer.getPassword());

        System.out.println("login result is : "+ loginresult);
        if (loginresult.equals("Login Successful")) {
            System.out.println("Customer Login is successful...");
            String token = jwtUtil.generateToken(customer.getMail());
            System.out.println("The Token is:"+token);

            //build cookie for JWT
            ResponseCookie cookie = ResponseCookie.from("access_token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Lax")
                    .maxAge(Duration.ofDays(2))
                    .build();

            //Sending cookies.
            return ResponseEntity.accepted()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Login Succeed");

        } else if (loginresult.equals("user not found")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/custsignup")
    public String custsignup(@RequestBody Customer customer){
        System.out.println("Cust Sign up is calling : "+ customer);
        try{
            String encodedPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(encodedPassword);
            customerSerivce.save(customer);
            cartService.createCart(customer);
            return "success";
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/AdminSignUp")
    public String  AdminSignUp(@RequestBody Admin admin){
        System.out.println("adminSignUp is called");
        try {
            String AdminSignUpResult = adminService.AdminSignUp(admin);
            return AdminSignUpResult;
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/adminLoginCheckup")
    public String adminLoginCheckup(@RequestBody Admin admin){
        System.out.println("Admin Loging Checkup is called ....");
        String loginCheckUpResult =  adminService.AdminLoginCheckup(admin);
        if(loginCheckUpResult.equals("Successful")){
            String token = jwtUtil.generateToken(admin.getAdminUsername());
            return token;
        } else if (loginCheckUpResult.equals("Invalid Credentials")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Add_Role")
    private ResponseEntity<?> Add_Role(@RequestBody Roles role) {
        try{
            adminService.AddingRole(role);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
