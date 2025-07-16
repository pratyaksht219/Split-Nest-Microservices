package com.app.expenseservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long paidByUserId;

    @NotBlank
    private String description;

    @Column(nullable = true)
    private Long groupId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime paymentDateTime;

    @ElementCollection
    @CollectionTable(name = "involved_user_ids", joinColumns = @JoinColumn(name = "expense_id"))
    @Column(name = "involved_user_id")
    private List<Long> involvedUserIds;
}
