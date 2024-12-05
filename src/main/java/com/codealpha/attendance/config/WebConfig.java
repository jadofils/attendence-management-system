package com.codealpha.attendance.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the "uploads" folder for initial sign-up profiles
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:src/main/java/com/codealpha/attendance/config/uploads/");
        
        // Map the "updatedUploads" folder for profile updates
        registry.addResourceHandler("/updatedUploads/**")
                .addResourceLocations("file:src/main/java/com/codealpha/attendance/config/updatedUploads/");
    }
}
