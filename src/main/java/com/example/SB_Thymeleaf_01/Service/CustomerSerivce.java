package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerSerivce {

    private CustomerRepository repo;

    public void CustomerSerivce(CustomerRepository repo){
        this.repo = repo;
    }

    public void save(Customer customer){
        repo.save(customer);
    }

}
