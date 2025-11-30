package com.Rootale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient llmWebClient() {
        return WebClient.builder().baseUrl("http://localhost:5000").build();
    }

    @Bean
    public WebClient ttsWebClient() {
        return WebClient.builder().baseUrl("https://api.tts-service.com").build(); // api 엔드포인트로 대체
    }

    @Bean
    public WebClient imageWebClient() {
        return WebClient.builder().baseUrl("https://api.image-service.com").build(); // api 엔드포인트로 대체
    }
}
