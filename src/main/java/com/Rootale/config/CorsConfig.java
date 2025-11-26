package com.Rootale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        //origin(fe, swagger)-> 서버
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8081",
                "http://localhost:8080",
                "http://10.0.2.2:8080",
                "http://ec2-13-125-90-89.ap-northeast-2.compute.amazonaws.com",
                "http://rootale.swagger.s3-website.ap-northeast-2.amazonaws.com",
                "https://localhost:3000",
                "https://localhost:8081",
                "https://localhost:8080",
                "https://10.0.2.2:8080",
                "https://ec2-13-125-90-89.ap-northeast-2.compute.amazonaws.com",
                "https://rootale.swagger.s3-website.ap-northeast-2.amazonaws.com"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
