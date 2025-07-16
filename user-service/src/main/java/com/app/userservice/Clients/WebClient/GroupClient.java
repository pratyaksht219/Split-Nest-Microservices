package com.app.userservice.Clients.WebClient;

import com.app.userservice.DTO.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GroupClient {

    @Autowired
    private WebClient userWebClient;
    @Autowired
    private WebClient groupWebClient;

    public GroupResponse getGroupById(Long groupId){
        GroupResponse fetchedGroupResponse = groupWebClient.get()
                .uri("/api/v1/groups/{groupId}",groupId)
                .retrieve()
                .bodyToMono(GroupResponse.class)
                .block();
        return fetchedGroupResponse;
    }
}
