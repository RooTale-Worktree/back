package com.Rootale.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
public class OpenApiConfig {
    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Bean
    public OpenAPI openAPI() {
        Server ec2Server = new Server()
                .url("http://ec2-13-125-90-89.ap-northeast-2.compute.amazonaws.com")
                .description("EC2 Production Server");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local Development Server");

        Info info = new Info()
                .title("API Document")
                .version("v5.28.1")
                .description("Rootale 백엔드 API 명세");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .servers(Arrays.asList(ec2Server,localServer))
                .info(info)
                .schemaRequirement("Bearer Authentication", securityScheme)
                .addSecurityItem(securityRequirement);
    }
}
