package com.example.SB_Thymeleaf_01;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.security.PrivateKey;

@Entity
public class Availableitems {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int item_id;

    private String brand_item_id;
    private int item_quantity;
    private int item_price;
    private int item_sold_count;

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

    @Override
    public String toString() {
        return "AvailableItems{" +
                "item_id=" + item_id +
                ", brand_item_id='" + brand_item_id + '\'' +
                ", item_quantity=" + item_quantity +
                ", item_price=" + item_price +
                ", item_sold_count=" + item_sold_count +
                '}';
    }
}
