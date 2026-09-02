package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerSerivce {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    public void CustomerSerivce(CustomerRepository repo){
        this.repo = repo;
    }

    public void save(Customer customer){
        repo.save(customer);
    }


    public List<Customer> showCustomers() {
        List<Customer> cust = repo.findAll();
        return cust;
    }

    public Optional<Customer> findAnyCustomer (String mail) {
        Optional<Customer> findingCustomer = repo.findByMail(mail);

        return findingCustomer;
    }

}
