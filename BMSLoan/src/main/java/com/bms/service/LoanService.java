package com.bms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.exception.InvalidTokenException;
import com.bms.model.Loan;

@Service
public interface LoanService {

	public ResponseEntity<Object> applyLoan(Loan loan,String cid,String token) throws InvalidTokenException;

	public List<Loan> getCustomerLoan(String token, String customer_id) throws InvalidTokenException;

	public List<Loan> getLoanByType(String loanType);
}
