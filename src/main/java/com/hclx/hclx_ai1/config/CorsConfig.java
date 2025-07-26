package com.hclx.hclx_ai1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("init cors");
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET","DELETE","POST","OPTIONS","PUT")
                .allowedHeaders("*").exposedHeaders("*")
                .maxAge(3600);

    }
}
