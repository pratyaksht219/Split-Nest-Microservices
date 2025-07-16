package com.app.Kafka.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddedToGroupEvent {
    private Long userId;
    private Long groupId;
}
