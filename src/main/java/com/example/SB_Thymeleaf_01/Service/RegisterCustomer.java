package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomer {

    @Autowired
    private CustomerRepository registerCustomerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(Customer customer){
        if (registerCustomerRepo.existsByMail(customer.getMail())){

            return "Email already exists";
        }else{
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));

            registerCustomerRepo.save(customer);

            return "Customer registered Successfuly!";
        }
    }
}
