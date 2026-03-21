package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.lang.invoke.CallSite;
import java.sql.SQLException;
import java.util.List;

@Service
public class ItemsService {

    @Autowired
    public ItemsRepository repoForAllItems;
    @Autowired
    public ItemsRepository saveTrendingItem;


    public List<Availableitems> showall() {

        System.out.println("ShowAll method in Items Service class is called ...");
        List<Availableitems> allitems = repoForAllItems.findAll();
        return allitems;
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
}
