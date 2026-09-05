package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cust")
public class CustomerController1 {

    @Autowired
    private CustomerSerivce customerSerivce;

    @GetMapping("/ShowAllCustomers")
    public List<Customer> ShowAllCustomers(){
        List<Customer> allcust = customerSerivce.showCustomers();

        return  allcust;
    }

    @GetMapping("/seeCSRF")
    public CsrfToken seeCSRF(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
