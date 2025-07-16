package com.app.debtservice.Service;

import com.app.debtservice.Clients.WebClient.GroupClient;
import com.app.debtservice.Clients.WebClient.UserClient;
import com.app.debtservice.DTO.DebtResponse;
import com.app.debtservice.DTO.ExpenseResponse;
import com.app.debtservice.Entity.Debt;
import com.app.debtservice.Entity.DebtStatus;
import com.app.debtservice.Repository.DebtRepository;
import com.app.debtservice.exceptions.APIException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DebtServiceImplementation implements DebtService{

    @Autowired
    private DebtRepository debtRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private GroupClient groupClient;

    @Override
    public void userDeleteEventConsumer(Long userId) {

    }

    @Override
    public void groupDeletionEventConsumer(Long groupId) {

    }
    @Transactional
    @Override
    public void expenseCreationEventConsumer(ExpenseResponse expenseResponse) {
        try{
            List<Debt> debtsToBeSaved = new ArrayList<>();
            for(Long userId: expenseResponse.getInvolvedUserIds()){
                if(expenseResponse.getPaidByUserId()!=null &&
                        !expenseResponse.getPaidByUserId().equals(userId)){
                    Debt debt = new Debt();

                    debt.setExpenseId(expenseResponse.getId());
                    debt.setGroupId(expenseResponse.getGroupId());
                    debt.setExpenseDescription(expenseResponse.getDescription());
                    debt.setTotalExpenseAmount(expenseResponse.getAmount());
                    debt.setPaidByUserId(expenseResponse.getPaidByUserId());
                    debt.setOwingUserId(userId);
                    debt.setOwedAmount(expenseResponse.getAmount());
                    debt.setIsActive(true);
                    debt.setDebtStatus(DebtStatus.PENDING);

                    BigDecimal share = expenseResponse.getAmount()
                            .divide(BigDecimal.valueOf(expenseResponse.getInvolvedUserIds().size()),
                                    MathContext.DECIMAL32)
                            .setScale(2, RoundingMode.HALF_UP);
                    debt.setOwedAmount(share);
                    debtsToBeSaved.add(debt);
                }
                debtRepository.saveAll(debtsToBeSaved);
            }
        }catch (Exception e){
            throw new APIException("Error while generating " +
                    "Debts for the expense: "+e.getMessage());
        }

    }

    @Override
    public void expenseDeletionEventConsumer(ExpenseResponse expenseResponse) {
        try{
            List<Debt> debtsToBeDeleted = debtRepository.findAllByExpenseId(expenseResponse.getId());
            if (debtsToBeDeleted.isEmpty()) {
                throw new APIException("No debts found for expense id: " + expenseResponse.getId());
            }
            for (Debt debt : debtsToBeDeleted) {
                debt.setIsActive(false);
            }
            debtRepository.saveAll(debtsToBeDeleted);
        }catch (Exception e){
            throw new APIException("Error while deleting " +
                    "Debts for the expense: "+e.getMessage());
        }
    }

    @Override
    public String settleDebt(Long debtId) {
        return "";
    }

    @Override
    public String forgiveDebt(Long debtId) {
        return "";
    }

    @Override
    public String deleteAllDebts() {
        return "";
    }

    @Override
    public String deleteDebt(Long debtId) {
        return "";
    }

    @Override
    public String deleteDebtByUserId(Long userId) {
        return "";
    }

    @Override
    public String deleteDebtByGroupId(Long groupId) {
        return "";
    }

    @Override
    public String deleteDebtByExpenseId(Long expenseId) {
        return "";
    }

    @Override
    public String deleteDebtByUserIdAndGroupId(Long userId, Long groupId) {
        return "";
    }

    @Override
    public String deleteDebtByUserIdAndExpenseId(Long userId, Long expenseId) {
        return "";
    }

    @Override
    public String deleteDebtByGroupIdAndExpenseId(Long groupId, Long expenseId) {
        return "";
    }

    @Override
    public List<DebtResponse> getAllDebts() {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByGroupId(Long groupId) {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByExpenseId(Long expenseId) {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByUserIdAndGroupId(Long userId, Long groupId) {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByUserIdAndExpenseId(Long userId, Long expenseId) {
        return List.of();
    }

    @Override
    public List<DebtResponse> getAllDebtsByGroupIdAndExpenseId(Long groupId, Long expenseId) {
        return List.of();
    }

    @Override
    public Boolean doesUserHaveUnsettledDebts(Long userId) {
        return null;
    }

    @Override
    public Boolean doesGroupHaveUnsettledDebts(Long groupId) {
        return null;
    }

    @Override
    public Boolean doesExpenseHaveUnsettledDebts(Long expenseId) {
        return null;
    }
}
