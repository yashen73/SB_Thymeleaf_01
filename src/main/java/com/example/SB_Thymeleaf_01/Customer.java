package com.example.SB_Thymeleaf_01;

import jakarta.persistence.*;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String mail;
    private int tele;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTele() {
        return tele;
    }

    public void setTele(int tele) {
        this.tele = tele;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", tele=" + tele +
                ", password='" + password + '\'' +
                '}';
    }
}
