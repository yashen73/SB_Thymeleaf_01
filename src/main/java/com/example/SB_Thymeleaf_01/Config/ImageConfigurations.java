package com.example.SB_Thymeleaf_01.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfigurations implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry regisrty ) {
        regisrty.addResourceHandler("/storage/app**")
                .addResourceLocations("file:/product_thumbnail_images")
                .addResourceLocations("file:/detail_images");
    }
}
