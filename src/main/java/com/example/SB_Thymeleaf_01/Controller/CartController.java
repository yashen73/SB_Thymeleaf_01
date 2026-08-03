package com.example.SB_Thymeleaf_01.Controller;


import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.CartItem;
import com.example.SB_Thymeleaf_01.Security.JwtUtil;
import com.example.SB_Thymeleaf_01.Service.CartService;
import com.stripe.model.issuing.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;

    private JwtUtil jwtUtil = new JwtUtil();


    @PostMapping("/AddtoCart")
    public ResponseEntity<String> AddtoCart(@RequestBody CartItem item, @RequestHeader("Authorization") String token){
        System.out.println("AddtoCart in Cart controller is called.");

        String customermail = jwtUtil.extractUsername(token);

        try {
            cartService.addItemToCart(item, customermail);
            return ResponseEntity.ok("Success");
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/RemoveFromCart")
    public String RemoveFromCart(){
        try {
            return "Success";
        } catch (RuntimeException e) {
             throw new RuntimeException(e);
        }
    }

    @GetMapping("/ShowCartItems")
    public List<Availableitems> ShowCartItems(@RequestParam("token") String token){
        System.out.println("ShowCartItems is called in carcontorller ....");
        String emailForCartItem =  jwtUtil.extractUsername(token);
        return cartService.getAllItemsInCart(emailForCartItem);
    }
}
