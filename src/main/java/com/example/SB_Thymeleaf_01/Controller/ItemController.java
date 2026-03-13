package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import com.example.SB_Thymeleaf_01.Service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemsService serviceForItems; //serviceforItems - to represent all availble items.

    @GetMapping("/showAllItems")
    public List<Availableitems> showAllItems(Model model){
        System.out.println(" ");
        System.out.println("Item list is calling");
        List<Availableitems> allItemsList = serviceForItems.showall();
        model.addAttribute("allItemsList", allItemsList);
        System.out.println("These are the items : "+allItemsList);

        return allItemsList;
    }
}