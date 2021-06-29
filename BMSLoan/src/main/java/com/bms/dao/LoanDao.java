package com.bms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.model.Loan;
@Repository
public interface LoanDao extends JpaRepository<Loan, String> {

	List<Loan> findByCid(String customer_id);

}
