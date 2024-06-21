package com.example.apigateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("https://hotif.emf-informatique.ch", "https://dufourj.emf-informatique.ch", "http://localhost:5500")
                .allowedHeaders("*")
                .allowCredentials(true);
                
    }
}