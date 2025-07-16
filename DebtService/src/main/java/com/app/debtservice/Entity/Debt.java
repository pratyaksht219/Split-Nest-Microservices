package com.app.debtservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Debt {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    private Long expenseId;

    @Column(nullable = true)
    private Long groupId;
    @NotNull
    private Long paidByUserId;
    @NotBlank
    private String expenseDescription;
    @NotNull
    private BigDecimal totalExpenseAmount;
    @NotNull
    private Long owingUserId;
    @NotNull
    private BigDecimal owedAmount;

    @CreationTimestamp
    private LocalDateTime paymentTimeStamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebtStatus debtStatus = DebtStatus.PENDING;

    @Column(nullable = true)
    private LocalDateTime debtSettlementTimeStamp;

    @Column(nullable = false)
    private Boolean isActive = true;



}
