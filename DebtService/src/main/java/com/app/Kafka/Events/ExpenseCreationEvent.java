package com.app.Kafka.Events;

import com.app.debtservice.DTO.ExpenseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCreationEvent {
    private ExpenseResponse expenseResponse;
}
