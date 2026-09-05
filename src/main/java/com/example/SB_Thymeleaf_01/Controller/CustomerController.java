package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Models.Availableitems;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    @Autowired
    private CustomerSerivce custormerService;
    @Autowired
    private LoginService loginservice;
    @Autowired
    private RegisterCustomer registercustomer;
    @Autowired
    private ItemsService findAnyItem;
    @Autowired
    private CartService serviceforCart;

    public CustomerController(CustomerSerivce service){
        this.custormerService = service;
    }

    @RequestMapping("/")
    public String index(){
        System.out.println("index page is calling...");
        return "index";
    }

    @GetMapping("/index")
    public String indexafterlogin(){
        return "index";
    }

    @RequestMapping("/CustomerSignup")
    public String CustomerSignup(){
        System.out.println("Customer Sign Up is loaded ...");
        return "CustomerSignup";
    }

    @PostMapping("/signup")
    public String save(@ModelAttribute Customer customer, Model model, RedirectAttributes redirectAttributes){
        System.out.println("sign up is called  . . . .");
        String regsitrationResult =registercustomer.register(customer);

        if(regsitrationResult == "Email already exists"){
            model.addAttribute("message", "This Email is already has been registered . . . ");
            System.out.println("rejected due to existing email . . . ");
            return "auth/login";
        }else {
            System.out.println("Registration Successfull .....");

            // Send success message after redirect
            redirectAttributes.addFlashAttribute("SignupSucceedMessage", "Successful");
            //Calling Service to Create an cart on Cart Table....
            String createCartResult = serviceforCart.createCart(customer);
            if (createCartResult.equals("Success")){
                return "CustomerLogin";
            }else {
                return "auth/login";
            }

        }
    }


    @RequestMapping("/CustomerLogin")
    public String CustomerLogin(){
        System.out.println("Customer login is loaded ...");
        return "CustomerLogin";
    }


    @PostMapping("/login")
    public String login(@RequestParam String mail, @RequestParam String password, HttpSession session, Model model, RedirectAttributes redirectAttributes){
        System.out.println("Login post mapping in controller is called .... ");

        String loginResult = loginservice.customerlogincheckup(mail, password);

        if ("Login Successful".equals(loginResult)){

            System.out.println("Login is Successful & Customer "+mail+" is login....");
            session.setAttribute("loggedCustomer", mail);

            return "index";

        }else if("Invalid login".equals(loginResult)){
            System.out.println("login is invalid due to missmatch of password and email. . . ");
            model.addAttribute("alertmessage", loginResult);
            redirectAttributes.addFlashAttribute("SingupFailedMessage", "failed");
            return "redirect:/CustomerLogin";

        }else{

            model.addAttribute("alertmessage", loginResult);

            return "CustomerLogin";
        }
    }

    @GetMapping("/product/productdetail/{productid}")
    public String getProductdetail(@PathVariable Long productid, Model model){
        System.out.println("product detail method in product controller is called...");

        try{
            Availableitems item = findAnyItem.findSpecificItem(productid);
            model.addAttribute("item",item);
            return "ProductDetails";
        }catch(RuntimeException e) {
            e.printStackTrace();
            return "index";
        }
    }

    @GetMapping("/Profile")
    public String Profile(){
        return "Profile";
    }

    @GetMapping("/itemsforbrands")
    public  String itemsforbrands(){
        return "BrandViseProducts";
    }

    @GetMapping("/AboutUs")
    public String AboutUs(){
        return "AboutUs";
    }

    @GetMapping("/Locations")
    public String Locations(){
        return "Locations";
    }

    @GetMapping("/PaymentSuccess")
    public String PaymentSuccess(){
        return "PaymentSucced";
    }

    @GetMapping("/PaymentUnsuccess")
    public String PaymentUnsuccess(){
        return "PaymentCancled";
    }

}

