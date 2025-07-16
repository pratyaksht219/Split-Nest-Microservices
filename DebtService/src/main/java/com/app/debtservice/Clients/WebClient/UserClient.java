package com.app.debtservice.Clients.WebClient;

import com.app.debtservice.DTO.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {


    @Autowired
    private WebClient userWebClient;

    public UserResponse getUserById(Long userId){
        return userWebClient.get()
                .uri("/users/{userId}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }
}
