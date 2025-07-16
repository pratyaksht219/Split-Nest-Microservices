package com.app.expenseservice.Service;



import com.app.Kafka.Events.ExpenseCreationEvent;
import com.app.Kafka.Events.ExpenseDeletionEvent;
import com.app.expenseservice.Clients.Kafka.Producer;
import com.app.expenseservice.Clients.WebClient.UserClient;
import com.app.expenseservice.DTO.ExpenseRequest;
import com.app.expenseservice.DTO.ExpenseResponse;
import com.app.expenseservice.Entity.Expense;
import com.app.expenseservice.Repository.ExpenseRepository;
import com.app.expenseservice.exceptions.APIException;
import com.app.expenseservice.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExpenseServiceImplementation implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private Producer producer;

    @Transactional
    @Override
    public String addExpense(ExpenseRequest expenseRequest) {
        try{
            Expense expense = new Expense();
            expense.setAmount(expenseRequest.getAmount());
            expense.setDescription(expenseRequest.getDescription());
            expense.setPaidByUserId(expenseRequest.getPaidByUserId());
            if(expenseRequest.getGroupId()!=null){
                expense.setGroupId(expenseRequest.getGroupId());
            }
            expense.setInvolvedUserIds(expenseRequest.getInvolvedUserIds());
            expenseRepository.save(expense);

            //triggering ExpenseCreationEvent
            producer.sendExpenseCreationEvent(new ExpenseCreationEvent(
                    modelMapper.map(expense, ExpenseResponse.class))
            );
            return "Expense Created Successfully";
        }catch (Exception e){
            throw new APIException("Error while creating expense\n error: "+e.getMessage());
        }
    }

    @Override
    public String deleteExpense(Long expenseId) {
        try{
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(()->new ResourceNotFoundException("Expense", "id", expenseId));

            //triggering expense deletion event
            producer.sendExpenseDeletionEvent(new ExpenseDeletionEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));

            expenseRepository.delete(expense);
            return "Expense Deletion successful";
        }catch(Exception e){
            throw new APIException("Error while deleting expense\n error: "+e.getMessage());
        }
    }
    @Transactional
    @Override
    public String updateExpense(Long expenseId, ExpenseRequest expenseRequest) {
        try{


            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(()->new ResourceNotFoundException("Expense", "id", expenseId));

            producer.sendExpenseDeletionEvent(new ExpenseDeletionEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));
            expense.setAmount(expenseRequest.getAmount());
            expense.setDescription(expenseRequest.getDescription());
            expense.setPaidByUserId(expenseRequest.getPaidByUserId());
            expense.setGroupId(expenseRequest.getGroupId());
            expense.setInvolvedUserIds(expenseRequest.getInvolvedUserIds());

            // triggering expense deletion event and expense creation event
            // to make sure the splits created for the updated expense are deleted
            // and new splits are created for the updated expense


            expenseRepository.save(expense);

            producer.sendExpenseCreationEvent(new ExpenseCreationEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));
            return "Expense Updated Successfully";
        }catch (Exception e){
            throw new APIException("Error while updating expense\n error: "+e.getMessage());
        }
    }

    @Transactional
    @Override
    public String addInvolvedUser(Long expenseId, Long userId) {

        try {

            //check is the user to be added is valid
            if (userClient.getUserById(userId) == null) {
                throw new ResourceNotFoundException("User", "id", userId);
            }

            //retrieve the expense from the repository
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));

            //trigger expenseDeletionEvent
            producer.sendExpenseDeletionEvent(new ExpenseDeletionEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));

            //add the users
            expense.getInvolvedUserIds().add(userId);

            //persist the data into the expense repository
            expenseRepository.save(expense);

            //trigger expense creation event
            producer.sendExpenseCreationEvent(new ExpenseCreationEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));

            return "Involved user added successfully";

        }catch (Exception e){
            throw new APIException("Error while adding involved user\n error: "+e.getMessage());
        }
    }

    @Transactional
    @Override
    public String removeInvolvedUser(Long expenseId, Long userId) {
        try {

            //check is the user to be removed is valid
            if (userClient.getUserById(userId) == null) {
                throw new ResourceNotFoundException("User", "id", userId);
            }

            //retrieve the expense from the repository
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));

            // Check if the user is involved in the expense
            if (!expense.getInvolvedUserIds().contains(userId)) {
               throw new APIException("User is not involved in the expense");
            }

            //trigger expenseDeletionEvent
            producer.sendExpenseDeletionEvent(new ExpenseDeletionEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));

            //remove the users
            expense.getInvolvedUserIds().remove(userId);

            //persist the data into the expense repository
            expenseRepository.save(expense);

            //trigger expense creation event
            producer.sendExpenseCreationEvent(new ExpenseCreationEvent(
                    modelMapper.map(expense, ExpenseResponse.class)
            ));

            return "Involved user removed successfully";

        }catch (Exception e){
            throw new APIException("Error while removing involved user\n error: "+e.getMessage());
        }
    }

    @Override
    public ExpenseResponse getExpenseById(Long expenseId) {
        try{
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));
            return modelMapper.map(expense, ExpenseResponse.class);
        }catch (Exception e){
            throw new APIException("Error while fetching expense\n error: "+e.getMessage());
        }

    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        try{
            List<Expense> expenses = expenseRepository.findAll();
            if(expenses.isEmpty()){
                throw new APIException("No expenses found");
            }
            return expenses.stream()
                    .map(expense->modelMapper.map(expense, ExpenseResponse.class))
                    .toList();
        }catch (Exception e){
            throw new APIException("Error while fetching expenses\n error: "+e.getMessage());
        }
    }

    @Override
    public List<ExpenseResponse> getAllExpensesByUserId(Long userId) {
        try{
            List<Expense> expenses = expenseRepository.findAllByInvolvedUserIdsContaining(userId);
            if(expenses.isEmpty()){
                throw new APIException("No expenses found for user id "+userId);
            }
            return expenses.stream()
                    .map(expense->modelMapper.map(expense, ExpenseResponse.class))
                    .toList();
        }catch (Exception e){
            throw new APIException("Error while fetching expenses\n error: "+e.getMessage());
        }
    }

    @Override
    public List<ExpenseResponse> getAllExpensesByGroupId(Long groupId) {
        try{
            List<Expense> expenses = expenseRepository.findAllByGroupId(groupId);
            if(expenses.isEmpty()){
                throw new APIException("No expenses found for user id "+groupId);
            }
            return expenses.stream()
                    .map(expense->modelMapper.map(expense, ExpenseResponse.class))
                    .toList();
        }catch (Exception e){
            throw new APIException("Error while fetching expenses\n error: "+e.getMessage());
        }
    }

    @Override
    public List<ExpenseResponse> getAllExpensesByGroupAndUserId(Long groupId, Long userId) {
        try{
            List<Expense> expenses = expenseRepository.findAllByGroupIdAndInvolvedUserIdsContaining(groupId, userId);
            if(expenses.isEmpty()){
                throw new APIException("No expenses found for user id "+userId+" and group id "+groupId);
            }
            return expenses.stream()
                    .map(expense->modelMapper.map(expense, ExpenseResponse.class))
                    .toList();
        }catch (Exception e){
            throw new APIException("Error while fetching expenses\n error: "+e.getMessage());
        }
    }
}
