package com.app.expenseservice.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {

    @NotBlank
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long paidByUserId;

    @Column(nullable = true)
    private Long groupId;

    private List<Long> involvedUserIds = new ArrayList<>();

}
