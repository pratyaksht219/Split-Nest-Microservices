package com.app.groupservice.Clients.WebClient.Kafka;

import com.app.Kafka.Events.UserDeletedEvent;
import com.app.groupservice.Service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final GroupService groupService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @KafkaListener(topics = "user-deleted-event-topic", groupId = "user-service")
    public void consumeUserDeletedEvent(UserDeletedEvent userDeletedEvent){
        groupService.removedUserEvent(userDeletedEvent.getUserId());
    }

}
