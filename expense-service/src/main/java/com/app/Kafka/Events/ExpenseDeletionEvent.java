package com.app.Kafka.Events;

import com.app.expenseservice.DTO.ExpenseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDeletionEvent {
    private ExpenseResponse expenseResponse;
}
