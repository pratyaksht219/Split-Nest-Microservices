package com.app.debtservice.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Long paidByUserId;
    private LocalDateTime paymentDateTime;
    @Column(nullable = true)
    private Long groupId;
    private List<Long> involvedUserIds;
}
