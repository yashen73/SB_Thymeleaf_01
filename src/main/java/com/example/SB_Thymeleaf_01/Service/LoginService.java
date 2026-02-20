package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private CustomerRepository repoForLogin;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public String customerlogincheckup(String mail, String password){
        System.out.println("Customer Login checkup in Login SERVICE is called for "+ mail);
        Optional<Customer> loginCustomer = repoForLogin.findByMail(mail);
        System.out.println(loginCustomer);

        if(loginCustomer.isEmpty()){
            return "user not found";
        } else if (!passwordEncoder.matches(password, loginCustomer.get().getPassword()) ) {
            return "Invalid login";
        }else{
            return "Login Successful";
        }

    }
}
