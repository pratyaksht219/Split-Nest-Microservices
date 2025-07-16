package com.app.expenseservice.Repository;

import com.app.expenseservice.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByInvolvedUserIdsContaining(Long userId);

    List<Expense> findAllByGroupId(Long groupId);

    List<Expense> findAllByGroupIdAndInvolvedUserIdsContaining(Long groupId, Long userId);
}
