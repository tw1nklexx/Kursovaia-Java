package com.example.railway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/trains").setViewName("trains");
        registry.addViewController("/about_author").setViewName("about_author");
        registry.addViewController("/admin/passengers").setViewName("passengers");
        registry.addViewController("/admin/new_train").setViewName("new_train");
        registry.addViewController("/admin/edit_train").setViewName("edit_train");
        registry.addViewController("/admin/new_passenger").setViewName("new_passenger");
        registry.addViewController("/admin/edit_passenger").setViewName("edit_passenger");
        registry.addViewController("/admin/statistics").setViewName("statistics");
    }
}
