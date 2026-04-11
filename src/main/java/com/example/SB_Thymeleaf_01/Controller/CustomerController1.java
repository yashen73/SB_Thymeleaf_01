package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import org.springframework.beans.factory.annotation.Autowired;
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
}
