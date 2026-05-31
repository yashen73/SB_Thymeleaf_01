package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Cart;
import com.example.SB_Thymeleaf_01.CartItem;
import com.example.SB_Thymeleaf_01.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CartItemRepo;
import com.example.SB_Thymeleaf_01.Repositories.CartRepo;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import com.stripe.model.issuing.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo repoforCart;
    @Autowired
    private CustomerRepository custrepo;
    @Autowired
    private CartItemRepo cartItemRepo;





    //Creating Cart for customer
    public String createCart(Customer customer){
        Optional<Customer> newCustomer = custrepo.findByMail(customer.getMail());
        try{
            Cart newCart = new Cart();
            newCart.setCustomer(newCustomer.get()); //Attaching custormer to the cart
            repoforCart.save(newCart);              //Saving cart
            return "Success";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


    //Adding Item For registered customer
    public String addItemToCart(CartItem item, String mail){

        Optional<Customer> newCustomer = custrepo.findByMail(mail);
        Optional<Cart> newCart = repoforCart.findByCustomerId(newCustomer.get().getId());

        try{
            item.setCart(newCart.get());
            cartItemRepo.save(item);
            return "Success";
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Removing Item from cart
    public String removeItemFromCart(CartItem item, String mail){

        Optional<Customer> newCustomer = custrepo.findByMail(mail);

        try{

            return "Success";
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
