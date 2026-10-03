package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.Exceptions.DuplicateEmailException;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.hibernate.sql.ast.spi.SqlAliasBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public String save(Customer customer){

        Optional<Customer> checkExistence = repo.findByMail(customer.getMail());
        if(checkExistence.isPresent()) {
            System.out.println("customer is existing...");
            throw new DuplicateEmailException("This email exists");
        }else {
            repo.save(customer);
            return "success";
        }
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
