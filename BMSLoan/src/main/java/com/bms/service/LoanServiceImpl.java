package com.bms.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.dao.LoanDao;
import com.bms.exception.InvalidTokenException;
import com.bms.model.Loan;
import com.bms.model.RateOfInterest;
import com.bms.model.ResponseForSuccess;
import com.bms.restclients.AuthFeign;

@Service
public class LoanServiceImpl implements LoanService {

	
	@Autowired
	private LoanDao ld;
	@Autowired
	private AuthFeign af;
	@Override
	public ResponseEntity<Object> applyLoan(Loan loan, String cid,String token) throws InvalidTokenException {
		if (af.getValidity(token).getBody().isValid()) {
			long count=ld.count();
			loan.setLoan_no("L-"+(10000+count));
			loan.setApplyDate(new Date());
			RateOfInterest rate;
			if(loan.getType().equalsIgnoreCase("Education")) {
				rate=RateOfInterest.EDUCATION;
			}
			else if(loan.getType().equalsIgnoreCase("Personal")) {
				rate=RateOfInterest.PERSONAL;
			}
			else {
				rate=RateOfInterest.HOME;
			}
			loan.setRateOfInterest(rate.getRateOfInterest());
			loan.setCid(cid);
			loan.setStatus("applied");
			ld.save(loan);
			return new ResponseEntity<>(new ResponseForSuccess(loan.getType()+"Loan applied successfuly",cid,loan.getLoan_no(),"/loan/"+loan.getCid()+"getCustomerLoans"),HttpStatus.OK);
		
		}
		else {
			throw new InvalidTokenException("Invalid Token...,Login again");
		}
	}
	@Override
	public List<Loan> getCustomerLoan(String token, String customer_id) throws InvalidTokenException {
		if (af.getValidity(token).getBody().isValid()) {
			return ld.findByCid(customer_id);
		}
		else {
			throw new InvalidTokenException("Invalid Token ...,Login again");
		}
	}
	@Override
	public List<Loan> getLoanByType(String loanType) {
		return ld.findAll().stream().filter(l-> l.getType().equals(loanType)).collect(Collectors.toList());
		
	}
	
	
}
