package com.app.expenseservice.Clients.WebClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient userWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }
}
