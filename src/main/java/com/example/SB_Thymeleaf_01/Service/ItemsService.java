package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.CallSite;
import java.util.List;

@Service
public class ItemsService {

    @Autowired
    public ItemsRepository repoForAllItems;

    public List<Availableitems> showall() {

        System.out.println("ShowAll method in Items Service class is called ...");
        List<Availableitems> allitems = repoForAllItems.findAll();
        return allitems;
    }
}
