package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import com.example.SB_Thymeleaf_01.Service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

        @Autowired
        private ItemsService serviceForItems; //serviceforItems - to represent all availble items.
        @Autowired
        private ItemsService findAnyItem;

        @GetMapping("/showAllItems")
        public List<Availableitems> showAllItems(Model model){
            System.out.println(" ");
            System.out.println("Item list is calling");
            List<Availableitems> allItemsList = serviceForItems.showall();
            model.addAttribute("allItemsList", allItemsList);
            System.out.println("These are the items : "+allItemsList);

            return allItemsList;
        }

        @PutMapping("/updateAnItem")
        public Availableitems updateItem(@RequestBody Availableitems updatedItem){
            System.out.println("http://localhost:8080/item/updateAnItem is called...");
            try{
                serviceForItems.updatethedatabase(updatedItem);
                return serviceForItems.showAnItem(Long.valueOf(updatedItem.getItem_id()));
            }catch (RuntimeException e) {
                throw new RuntimeException("Unsuccessful");
            }
        }

        @DeleteMapping("/deleteAnItem")
    public Availableitems deleteItem(@RequestBody Availableitems deletedItem){
            System.out.println("delete An Item is called...");
            serviceForItems.deletefromdatabase(deletedItem);

            return serviceForItems.showAnItem(Long.valueOf(deletedItem.getItem_id()));
        }
}