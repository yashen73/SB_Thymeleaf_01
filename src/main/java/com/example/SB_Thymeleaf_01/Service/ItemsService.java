package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.Availableitems;
import com.example.SB_Thymeleaf_01.Repositories.ItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.List;

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

    public String SaveItem(Availableitems trendingItem, MultipartFile thumbnailImg, MultipartFile detailImg_1, MultipartFile detailImg_2){

        //Directory path
        String thumbnailImgPath = "C:/Users/MCTech/Downloads/SB_Thymeleaf_01/SB_Thymeleaf_01/src/main/resources/static/images/product-thumbnails/";
        String detailImgPath = "C:/Users/MCTech/Downloads/SB_Thymeleaf_01/SB_Thymeleaf_01/src/main/resources/static/images/detail_images/";

        File directoryForThumbnailImg = new File(thumbnailImgPath);
        File directoryForDetailImg = new File(detailImgPath);


        if(!directoryForDetailImg.exists()) {
            directoryForDetailImg.mkdir();
        }

        if(!directoryForThumbnailImg.exists()) {
            directoryForThumbnailImg.mkdir();
        }

        String extension = ".png";

        String newFileNameforDetailImg_01 = trendingItem.getItem_detail_img1_name() + extension;
        String newFileNameforDetailImg_02 = trendingItem.getItem_detail_img2_name() + extension;
        String newFileNameforThumbnailImg = trendingItem.getItem_thumbnailimg_name() + extension;

        File destinaionForDetailImg_01 = new File(detailImgPath+newFileNameforDetailImg_01);
        File destinationForDetailImg_02 = new File(detailImgPath+newFileNameforDetailImg_02);
        File destinationForThumbnailImg = new File(thumbnailImgPath+newFileNameforThumbnailImg);

        try{
            saveTrendingItem.save(trendingItem);
            detailImg_1.transferTo(destinaionForDetailImg_01);
            detailImg_2.transferTo(destinationForDetailImg_02);
            thumbnailImg.transferTo(destinationForThumbnailImg);
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
