package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.swing.plaf.OptionPaneUI;
import javax.xml.crypto.Data;
import java.lang.invoke.CallSite;
import java.sql.SQLException;
import java.util.*;

@Service
public class ItemsService {
    @Autowired
    public ItemsRepository itemrepo;
    @Autowired
    public ItemsRepository findanyitem;
    @Autowired
    public ItemsRepository repoForAllItems;
    @Autowired
    public ItemsRepository saveTrendingItem;
    @Autowired
    public ItemsRepository findfromrepo;

    List<Availableitems> allAvailableItemsInRepo = new ArrayList<>();

    public List<Availableitems> showall() {

        System.out.println("ShowAll method in Items Service class is called ...");
        List<Availableitems> allitems = repoForAllItems.findAll();
        return allitems;
    }

    public Availableitems showAnItem(Long Itemid){
        Optional<Availableitems> item = findanyitem.findById(Itemid);
        try{
            return item.get();
        }catch (NoSuchElementException e){
            throw new RuntimeException("Not Found");
        }
    }

    public String SaveItem(Availableitems trendingItem){

        try{
            saveTrendingItem.save(trendingItem);
            return "success";
        }catch (DataAccessException e) {

            e.printStackTrace();
            throw new RuntimeException("DB error Occured");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something Went Wrong.");
        }
    }

    public Availableitems findSpecificItem(Long item_id){
        Optional<Availableitems> throwProductDetail  = findfromrepo.findById(item_id);
        System.out.println(throwProductDetail);


        if (throwProductDetail.isPresent()){
            return throwProductDetail.get();
        }else {
            throw new RuntimeException("Item not Found :"  + item_id);
        }
    }

    @Transactional
    public void updatethedatabase(Availableitems item) {
        System.out.println("Item updating service class is called for"+item);
        try{
            Optional<Availableitems> item1 = findfromrepo.findById(Long.valueOf(item.getItem_id()));

                if(item1.isPresent()){
                    Availableitems existinItem = item1.get();
                    existinItem.setItem_name(item.getItem_name());
                    existinItem.setItem_price(item.getItem_price());
                    existinItem.setItem_quantity(item.getItem_quantity());
                    existinItem.setItem_sold_count(item.getItem_sold_count());
                    existinItem.setItem_thumbnailimg_name(item.getItem_thumbnailimg_name());
                    itemrepo.save(existinItem);
            }
        }catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void deletefromdatabase(Availableitems item){
        try {
            itemrepo.deleteById(Long.valueOf(item.getItem_id()));
        }catch(DataAccessException e) {
            e.printStackTrace();
        }
    }
}
