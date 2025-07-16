package com.app.debtservice.Clients.Kafka;

import com.app.Kafka.Events.ExpenseCreationEvent;
import com.app.Kafka.Events.GroupDeleteEvent;
import com.app.Kafka.Events.UserDeletedEvent;
import com.app.debtservice.Service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class Consumer {

    @Autowired
    private DebtService debtService;

    @TransactionalEventListener
    @KafkaListener(topics = "user-deleted-event-topic", groupId = "user-service")
    public void consumeUserDeletionEvent(UserDeletedEvent event){
        System.out.println("User Deleted Event Received: " + event);
        debtService.userDeleteEventConsumer(event.getUserId());
    }

    @TransactionalEventListener
    @KafkaListener(topics = "group-deleted-event-topic", groupId = "group-service")
    public void consumeGroupDeletionEvent(GroupDeleteEvent event){
        System.out.println("Group Deleted Event Received: " + event);
        debtService.groupDeletionEventConsumer(event.getGroupId());
    }

    @TransactionalEventListener
    @KafkaListener(topics = "expense-creation-event-topic", groupId = "expense-service")
    public void consumeExpenseCreationEvent(ExpenseCreationEvent event){
        System.out.println("Expense creation event received: " + event);
        debtService.expenseCreationEventConsumer(event.getExpenseResponse());
    }

    @TransactionalEventListener
    @KafkaListener(topics = "expense-deletion-event-topic", groupId = "expense-service")
    public void consumeExpenseDeletionEvent(ExpenseCreationEvent event){
        System.out.println("Expense deletion event received: " + event);
        debtService.expenseDeletionEventConsumer(event.getExpenseResponse());
    }
}
