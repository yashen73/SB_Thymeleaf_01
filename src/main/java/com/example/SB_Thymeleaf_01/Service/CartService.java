package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.Availableitems;
import com.example.SB_Thymeleaf_01.Models.Cart;
import com.example.SB_Thymeleaf_01.Models.CartItem;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Repositories.CartItemRepo;
import com.example.SB_Thymeleaf_01.Repositories.CartRepo;
import com.example.SB_Thymeleaf_01.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo repoforCart;
    @Autowired
    private CustomerRepository custrepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;





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

    public List<Availableitems> getAllItemsInCart(String emailToFindCart) {
        //Find Customer for Customer Id using mail....
        Optional<Customer> customerToFindCart = custrepo.findByMail(emailToFindCart);
        //Find Cart using Customer Id.....
        Optional<Cart> cart = cartRepo.findByCustomerId(customerToFindCart.get().getId());
        //Make returns of list of items....
        if(cart.isPresent()){
            //Find item's id according to the Cart ID.....
            List<CartItem> listOfItems = cartItemRepo.findByCart(cart.get());
            System.out.println(listOfItems);
            return null;
        }else {
            return null;
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
