package com.app.userservice.Clients.Kafka;

import com.app.Kafka.Events.UserDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    private KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate;
    public void sendUserDeletedEvent(UserDeletedEvent userDeletedEvent) {
        userDeletedEventKafkaTemplate.send(
                MessageBuilder
                        .withPayload(userDeletedEvent)
                        .setHeader(KafkaHeaders.GROUP_ID,"user-service")
                        .setHeader(KafkaHeaders.TOPIC,"user-deleted-event-topic")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.UserDeletedEvent")
                        .build()
        );
    }
}
