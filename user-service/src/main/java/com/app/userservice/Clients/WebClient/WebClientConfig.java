package com.app.userservice.Clients.WebClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }


    // userServiceClient
    @Bean
    public WebClient groupWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://localhost:8082").build();
    }
}
