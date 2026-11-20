package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.Admin;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Repositories.AdminRepo;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class NewUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //check where the username relates to an ADMIN....
        if(username.contains("admin")){

            Optional<Admin> admin = adminRepo.findByadminUsername(username);
            System.out.println("LoadUserByusername is called...");
            if (admin.isPresent()) {
                System.out.println("User Found and returning user.builder...");
                return User.builder()
                        .username(admin.get().getAdminUsername())
                        .password(admin.get().getAdminPassword())
                        .roles("ADMIN")
                        .build();
            }else {
                System.out.println("user Not Found...");
                throw new UsernameNotFoundException("User not Found" + username);
            }
        }else {

            Optional<Customer> customer = customerRepo.findByUsername(username);
            System.out.println("LoadUserByusername is called...");
            if (customer.isPresent()) {
                System.out.println("User Found and returning user.builder...");
                return User.builder()
                        .username(customer.get().getUsername())
                        .password(customer.get().getPassword())
                        .roles("CUSTOMER")
                        .build();
            }else {
                System.out.println("user Not Found...");
                throw new UsernameNotFoundException("User not Found" + username);
            }
        }
    }
}
