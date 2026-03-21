package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import com.example.SB_Thymeleaf_01.Service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ItemsService trendingItem;

    //Save Trending items Send from JS
    @PostMapping("/addProductOnTrendingItems")
    public ResponseEntity<String> addProductOnTrendingItems(@RequestBody Availableitems availbleItems) {
        System.out.println(availbleItems);//Show the item sends from JS

        try{
            String ResponsToSave = trendingItem.SaveItem(availbleItems);    //calling ItemsService.class to save items
            return ResponseEntity.ok(ResponsToSave);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
