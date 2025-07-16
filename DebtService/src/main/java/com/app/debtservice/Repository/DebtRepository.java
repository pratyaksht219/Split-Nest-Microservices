package com.app.debtservice.Repository;

import com.app.debtservice.Entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    List<Debt> findAllByExpenseId(Long id);
}
