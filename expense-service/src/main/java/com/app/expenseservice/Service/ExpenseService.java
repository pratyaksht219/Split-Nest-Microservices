package com.app.expenseservice.Service;

import com.app.expenseservice.DTO.ExpenseRequest;
import com.app.expenseservice.DTO.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {
    String addExpense(ExpenseRequest expenseRequest);
    //trigger split creation for the added request

    String deleteExpense(Long expenseId);
    //trigger split deletion related to the deleted expenses

    String updateExpense(Long expenseId, ExpenseRequest expenseRequest);
    //trigger split deletion and creation of a new split

    String addInvolvedUser(Long expenseId, Long userId);
    //trigger split deletion and creation of a new split

    String removeInvolvedUser(Long expenseId, Long userId);
    //trigger split deletion and creation of a new split



    //all the get methods
    ExpenseResponse getExpenseById(Long expenseId);
    List<ExpenseResponse> getAllExpenses();
    List<ExpenseResponse> getAllExpensesByUserId(Long userId);
    List<ExpenseResponse> getAllExpensesByGroupId(Long groupId);
    List<ExpenseResponse> getAllExpensesByGroupAndUserId(Long groupId, Long userId);
}
