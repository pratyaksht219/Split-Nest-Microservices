package com.app.debtservice.DTO;

import com.app.debtservice.Entity.DebtStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DebtResponse {

    private Long Id;
    private Long expenseId;
    @Column(nullable = true)
    private Long groupId = null;
    private String expenseDescription;
    private BigDecimal totalExpenseAmount;
    private Long owedUserId;
    private BigDecimal owedAmount;
    private LocalDateTime paymentTimeStamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebtStatus debtStatus = DebtStatus.PENDING;
    private LocalDateTime debtSettlementTimeStamp;
}
