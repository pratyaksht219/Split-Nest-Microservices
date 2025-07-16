package com.app.groupservice.Clients.WebClient;


import com.app.groupservice.DTO.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {

    @Autowired
    private WebClient userWebClient;

    public UserResponse getUserById(Long id){
        UserResponse fetchedUserResponse = userWebClient.get()
                .uri("api/v1/users/{id}", id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
        return fetchedUserResponse;
    }
}
