package com.app.userservice.Clients.Kafka;

import com.app.Kafka.Events.GroupDeleteEvent;
import com.app.Kafka.Events.UserAddedToGroupEvent;
import com.app.Kafka.Events.UserRemovedFromGroupEvent;
import com.app.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class Consumer {

    @Autowired
    private UserService userService;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @KafkaListener(topics = "group-delete-event-topic", groupId = "group-service")
    public void consumeGroupDeleteEvent(GroupDeleteEvent groupDeleteEvent){
        userService.groupDeletedEvent(groupDeleteEvent.getGroupId());

    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @KafkaListener(topics = "group-user-remove-event-topic", groupId = "group-service")
    public void consumeUserRemovedFromGroupEvent(UserRemovedFromGroupEvent userRemovedFromGroupEvent){
       userService.userRemovedFromGroupEvent(userRemovedFromGroupEvent.getUserId(),
               userRemovedFromGroupEvent.getGroupId());

    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @KafkaListener(topics = "group-user-add-event-topic", groupId = "group-service")
    public void consumeUserAddedToGroupEvent(UserAddedToGroupEvent userAddedToGroupEvent){
        userService.userAddedToGroupEvent(userAddedToGroupEvent.getUserId(),
                userAddedToGroupEvent.getGroupId());
    }
}
