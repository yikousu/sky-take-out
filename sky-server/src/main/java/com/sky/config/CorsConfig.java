package com.sky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // 允许的源，这里需要根据你的前端应用地址来设置
            .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
            .allowedHeaders("*"); // 允许的请求头
    }
}