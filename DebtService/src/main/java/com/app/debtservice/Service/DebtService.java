package com.app.debtservice.Service;

import com.app.debtservice.DTO.DebtResponse;
import com.app.debtservice.DTO.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DebtService {
    void userDeleteEventConsumer(Long userId);
    //deletes the debts related to the user
    void groupDeletionEventConsumer(Long groupId);
    //deletes the debts related to the group
    void expenseCreationEventConsumer(ExpenseResponse expenseResponse);
    //creates all the debt object entries for the given ExpenseResponse object
    void expenseDeletionEventConsumer(ExpenseResponse expenseResponse);
    //deletes the expense related debts

    String settleDebt(Long debtId);
    String forgiveDebt(Long debtId);


    String deleteAllDebts();
    String deleteDebt(Long debtId);
    String deleteDebtByUserId(Long userId);
    String deleteDebtByGroupId(Long groupId);
    String deleteDebtByExpenseId(Long expenseId);
    String deleteDebtByUserIdAndGroupId(Long userId, Long groupId);
    String deleteDebtByUserIdAndExpenseId(Long userId, Long expenseId);
    String deleteDebtByGroupIdAndExpenseId(Long groupId, Long expenseId);

    List<DebtResponse> getAllDebts();
    List<DebtResponse> getAllDebtsByUserId(Long userId);
    List<DebtResponse> getAllDebtsByGroupId(Long groupId);
    List<DebtResponse> getAllDebtsByExpenseId(Long expenseId);
    List<DebtResponse> getAllDebtsByUserIdAndGroupId(Long userId, Long groupId);
    List<DebtResponse> getAllDebtsByUserIdAndExpenseId(Long userId, Long expenseId);
    List<DebtResponse> getAllDebtsByGroupIdAndExpenseId(Long groupId, Long expenseId);

    Boolean doesUserHaveUnsettledDebts(Long userId);
    Boolean doesGroupHaveUnsettledDebts(Long groupId);
    Boolean doesExpenseHaveUnsettledDebts(Long expenseId);
}
