package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Models.Availableitems;
import com.example.SB_Thymeleaf_01.Service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

        @Autowired
        private ItemsService serviceForItems; //serviceforItems - to represent all availble items.
        @Autowired
        private ItemsService findAnyItem;

        //Save Trending items Send from JS
        @PostMapping("/addProductOnTrendingItems")
        public ResponseEntity<String> addProductOnTrendingItems(@RequestPart("addingDTO") Availableitems availbleItems,
                                                                @RequestParam ("fileForThumbnailImg") MultipartFile thumbnailImg,
                                                                @RequestParam("fileForDetailImg1") MultipartFile detailimg1,
                                                                @RequestParam("fileForDetailImg2") MultipartFile detailimg2) {
            System.out.println(availbleItems+"/n"+thumbnailImg + "/n" + detailimg1 + "/n"+detailimg2);//Show the item sends from JS

            try{

                String ResponsToSave = serviceForItems.SaveItem(availbleItems, thumbnailImg, detailimg1, detailimg2);    //calling ItemsService.class to save items

                System.out.println("/n"+"All images saved successfully....");

                return ResponseEntity.status(HttpStatus.CREATED).body(ResponsToSave);

            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


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
        public ResponseEntity<String> deleteItem(@RequestBody Availableitems deletedItem){
            System.out.println("delete An Item is called...");
            serviceForItems.deletefromdatabase(deletedItem);
            try {
                serviceForItems.showAnItem(Long.valueOf(deletedItem.getItem_id()));
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body("Can not Delete");
            }catch (RuntimeException e){
                return  ResponseEntity.ok("Succefullyy");
            }
            //return serviceForItems.showAnItem(Long.valueOf(deletedItem.getItem_id()));
        }
}