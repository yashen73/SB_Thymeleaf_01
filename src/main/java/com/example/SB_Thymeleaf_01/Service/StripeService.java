package com.example.SB_Thymeleaf_01.Service;


import com.example.SB_Thymeleaf_01.Models.Availableitems;
import com.example.SB_Thymeleaf_01.Models.ItemPaymentRequest;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import java.util.Optional;

@Service
public class StripeService {
    @Autowired
    public ItemsRepository itemrepo;



    public String createCheckoutSession(ItemPaymentRequest request){
        try {
            System.out.println(request);
            Optional<Availableitems> item = itemrepo.findById(request.getProductid());
            Availableitems item1 =  item.get();
            //calculating total amount according to the quantity sends from front
            Long amountcalculation = item1.getItem_price() * (long) request.getQuantity();
            System.out.println("ID: "+item1.getItem_id());
            System.out.println("Total Amount: "+amountcalculation);
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/PaymentSuccess")
                    .setCancelUrl("http://localhost:8080/PaymentUnsuccess")
                    .setClientReferenceId(request.getProductid().toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity((long)request.getQuantity())
                                    .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amountcalculation)
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item1.getItem_name())
                                                        .build()
                                                )
                                                .build()
                                    )
                                    .build()
                    )
                    .build();
            Session session= Session.create(params);
            System.out.println("The Url for given Checkout session : "+session.getUrl());
            return session.getUrl();

        }catch (Exception e){
            throw new RuntimeException("Stripe error : "+e.getMessage());
        }
    }
}