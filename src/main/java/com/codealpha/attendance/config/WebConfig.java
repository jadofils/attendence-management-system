package com.codealpha.attendance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Replace '/path/to/your/uploads/directory/' with the actual path to your uploads directory
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:ile:./uploads/\"");
    }
}
