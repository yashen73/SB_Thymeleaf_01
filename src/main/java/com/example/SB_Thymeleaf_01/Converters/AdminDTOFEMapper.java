package com.example.SB_Thymeleaf_01.Converters;

import com.example.SB_Thymeleaf_01.DTO.AdminDTO;
import com.example.SB_Thymeleaf_01.Models.Admin;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@Component
public class AdminDTOFEMapper {

    public static Admin toEntity(AdminDTO adminDTO) {
        Admin newadmin = new Admin();

        newadmin.setAdminUsername(adminDTO.getAdminUsername());
        newadmin.setAdminPassword(adminDTO.getAdminPassword());

        return newadmin;
    }
}

