package com.app.debtservice.Clients.WebClient;

import com.app.debtservice.DTO.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GroupClient {
    @Autowired
    private WebClient groupWebClient;


    public GroupResponse getGroupById(Long groupId){
        return groupWebClient.get()
                .uri("/groups/{groupId}", groupId)
                .retrieve()
                .bodyToMono(GroupResponse.class)
                .block();
    }
}
