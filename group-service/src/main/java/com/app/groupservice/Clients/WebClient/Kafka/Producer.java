package com.app.groupservice.Clients.WebClient.Kafka;

import com.app.Kafka.Events.GroupDeleteEvent;
import com.app.Kafka.Events.UserAddedToGroupEvent;
import com.app.Kafka.Events.UserRemovedFromGroupEvent;
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
    private KafkaTemplate<String, GroupDeleteEvent> groupDeleteEventKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, UserAddedToGroupEvent> userAddedToGroupEventKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, UserRemovedFromGroupEvent> userRemovedFromGroupEventKafkaTemplate;

    public void sendGroupDeleteEvent(GroupDeleteEvent groupDeleteEvent){
        logger.debug("Sending Group Delete Event to Kafka");
        groupDeleteEventKafkaTemplate.send(
                MessageBuilder.withPayload(groupDeleteEvent)
                        .setHeader(KafkaHeaders.TOPIC, "group-deleted-event-topic")
                        .setHeader(KafkaHeaders.GROUP_ID, "group-service")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.GroupDeleteEvent")
                .build());
    }

    public void sendUserAddedToGroupEvent(UserAddedToGroupEvent userAddedToGroupEvent){
        logger.debug("Sending Group Delete Event to Kafka");
        groupDeleteEventKafkaTemplate.send(
                MessageBuilder.withPayload(userAddedToGroupEvent)
                        .setHeader(KafkaHeaders.TOPIC, "group-user-add-event-topic")
                        .setHeader(KafkaHeaders.GROUP_ID, "group-service")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.UserAddedToGroupEvent")
                        .build());
    }

    public void sendUserRemovedFromGroupEvent(UserRemovedFromGroupEvent userRemovedFromGroupEvent){
        logger.debug("Sending Group Delete Event to Kafka");
        groupDeleteEventKafkaTemplate.send(
                MessageBuilder.withPayload(userRemovedFromGroupEvent)
                        .setHeader(KafkaHeaders.TOPIC, "group-user-remove-event-topic")
                        .setHeader(KafkaHeaders.GROUP_ID, "group-service")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.UserRemovedFromGroupEvent")
                        .build());
    }


}
