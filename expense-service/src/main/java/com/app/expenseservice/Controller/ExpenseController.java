package com.app.expenseservice.Controller;

import com.app.expenseservice.DTO.ExpenseRequest;
import com.app.expenseservice.DTO.ExpenseResponse;
import com.app.expenseservice.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expenses")
    public ResponseEntity<String> createNewExpense(@Valid @RequestBody ExpenseRequest expenseRequest){
        String response = expenseService.addExpense(expenseRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/expenses/{expenseId}/add-involved-user/{userId}")
    public ResponseEntity<String> addInvolvedUser(@Valid@PathVariable Long userId, @Valid @PathVariable Long expenseId) {
        String response = expenseService.addInvolvedUser(expenseId, userId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/expenses/{expenseId}/remove-involved-user/{userId}")
    public ResponseEntity<String> removeInvolvedUser(@Valid@PathVariable Long userId, @Valid @PathVariable Long expenseId) {
        String response = expenseService.removeInvolvedUser(expenseId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/expenses/{expenseId}")
    public ResponseEntity<java.lang.String> put(@Valid @PathVariable Long expenseId, @Valid @RequestBody ExpenseRequest expenseRequest) {
        String response = expenseService.updateExpense(expenseId, expenseRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<String> delete(@Valid @PathVariable Long expenseId) {
        String response = expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expenses/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@Valid @PathVariable Long expenseId) {
        ExpenseResponse expenseResponse = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expenseResponse);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> expenseResponseList = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenseResponseList);
    }
    @GetMapping("/expenses/group/{groupId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroupId(@Valid @PathVariable Long groupId) {
        List<ExpenseResponse> expenseResponseList = expenseService.getAllExpensesByGroupId(groupId);
        return ResponseEntity.ok(expenseResponseList);
    }
    @GetMapping("/expenses/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUserId(@Valid @PathVariable Long userId) {
        List<ExpenseResponse> expenseResponseList = expenseService.getAllExpensesByUserId(userId);
        return ResponseEntity.ok(expenseResponseList);
    }

    @GetMapping("/expenses/group/{groupId}/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUserIdAndGroupId(@Valid @PathVariable Long userId,@Valid @PathVariable Long groupId) {
        List<ExpenseResponse> expenseResponseList = expenseService.getAllExpensesByGroupAndUserId(groupId,userId);
        return ResponseEntity.ok(expenseResponseList);
    }

}
