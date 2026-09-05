package com.example.SB_Thymeleaf_01.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Availableitems {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int item_id;
    
    private String item_name;
    private String brand_item_id;
    private int item_quantity;
    private int item_price;
    private int item_sold_count;
    private String item_thumbnailimg_name;
    private String item_detail_img1_name;
    private String item_detail_img2_name;
    private String item_views;
    private String item_rates;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getBrand_item_id() {
        return brand_item_id;
    }

    public void setBrand_item_id(String brand_item_id) {
        this.brand_item_id = brand_item_id;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public int getItem_sold_count() {
        return item_sold_count;
    }

    public void setItem_sold_count(int item_sold_count) {
        this.item_sold_count = item_sold_count;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_thumbnailimg_name() {
        return item_thumbnailimg_name;
    }

    public void setItem_thumbnailimg_name(String item_thumbnaimg_name) {
        this.item_thumbnailimg_name = item_thumbnaimg_name;
    }

    public String getItem_detail_img1_name() {
        return item_detail_img1_name;
    }

    public void setItem_detail_img1_name(String item_detail_img1_name) {
        this.item_detail_img1_name = item_detail_img1_name;
    }

    public String getItem_detail_img2_name() {
        return item_detail_img2_name;
    }

    public void setItem_detail_img2_name(String item_detail_img2_name) {
        this.item_detail_img2_name = item_detail_img2_name;
    }

    public String getItem_views() {
        return item_views;
    }

    public void setItem_views(String item_views) {
        this.item_views = item_views;
    }

    public String getItem_rates() {
        return item_rates;
    }

    public void setItem_rates(String item_rates) {
        this.item_rates = item_rates;
    }

    @Override
    public String toString() {
        return "Availableitems{" +
                "item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", brand_item_id='" + brand_item_id + '\'' +
                ", item_quantity=" + item_quantity +
                ", item_price=" + item_price +
                ", item_sold_count=" + item_sold_count +
                ", item_thumbnailimg_name='" + item_thumbnailimg_name + '\'' +
                ", item_detail_img1_name='" + item_detail_img1_name + '\'' +
                ", item_detail_img2_name='" + item_detail_img2_name + '\'' +
                ", item_views='" + item_views + '\'' +
                ", item_rates='" + item_rates + '\'' +
                '}';
    }
}
