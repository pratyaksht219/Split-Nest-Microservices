package com.app.Kafka.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRemovedFromGroupEvent {
    private Long userId;
    private Long groupId;
}
