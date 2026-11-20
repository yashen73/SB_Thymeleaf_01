package com.example.SB_Thymeleaf_01.Models;

import jakarta.persistence.*;

@Entity
public class Roles {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String roles;

    public long getId() {
        return id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", roles='" + roles + '\'' +
                '}';
    }
}
