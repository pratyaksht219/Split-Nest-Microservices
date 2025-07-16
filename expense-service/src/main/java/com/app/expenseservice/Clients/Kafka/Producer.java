package com.app.expenseservice.Clients.Kafka;

import com.app.Kafka.Events.ExpenseCreationEvent;
import com.app.Kafka.Events.ExpenseDeletionEvent;
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
    private KafkaTemplate<String, ExpenseCreationEvent> expenseCrationEventKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, ExpenseDeletionEvent> expenseDeletionEventKafkaTemplate; // <--->


    public void sendExpenseCreationEvent(ExpenseCreationEvent expenseCreationEvent) {
        expenseCrationEventKafkaTemplate.send(
                MessageBuilder.withPayload(expenseCreationEvent)
                        .setHeader(KafkaHeaders.TOPIC, "expense-creation-event-topic")
                        .setHeader(KafkaHeaders.GROUP_ID, "expense-service")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.ExpenseCreationEvent")
                        .build()
        );
        logger.debug("Event sent to Kafka: {} on the topic {}", expenseCreationEvent, "split-calculation-topic");
    }
    public void sendExpenseDeletionEvent(ExpenseDeletionEvent expenseDeletionEvent) {
        expenseDeletionEventKafkaTemplate.send(
                MessageBuilder.withPayload(expenseDeletionEvent)
                        .setHeader(KafkaHeaders.TOPIC, "expense-deletion-event-topic")
                        .setHeader(KafkaHeaders.GROUP_ID, "expense-service")
                        .setHeader("__TypeId__", "com.app.Kafka.Events.ExpenseDeletionEvent")
                        .build()
        );
        logger.debug("Event sent to Kafka: {} on the topic {}", expenseDeletionEvent, "split-calculation-topic");
    }




}
