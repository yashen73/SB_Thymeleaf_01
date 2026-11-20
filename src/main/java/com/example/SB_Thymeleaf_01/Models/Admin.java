package com.example.SB_Thymeleaf_01.Models;


import jakarta.persistence.*;

import javax.management.relation.Role;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int adminId;

    @Column(nullable = true, unique = true, length = 50)
    public String adminUsername;

    @Column(nullable = false)
    public String adminPassword;

    @Column(updatable = false)
    private Instant createdAt;

    @Column(length = 30)
    private Set<Role> roles = new HashSet<>();



    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminUsername='" + adminUsername + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                '}';
    }
}
