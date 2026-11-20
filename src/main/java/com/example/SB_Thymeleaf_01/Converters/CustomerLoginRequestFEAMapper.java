package com.example.SB_Thymeleaf_01.Converters;

import com.example.SB_Thymeleaf_01.DTO.CustomerLoginRequest;
import com.example.SB_Thymeleaf_01.Models.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerLoginRequestFEAMapper {
    public static Customer toEntity(CustomerLoginRequest request) {
        Customer customer = new Customer();
        customer.setMail(request.getMail());
        customer.setPassword(request.getPassword());

        return customer;
    }
}
