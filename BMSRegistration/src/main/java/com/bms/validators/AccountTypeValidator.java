package com.bms.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bms.model.Customer;

public class AccountTypeValidator implements ConstraintValidator<AccountType, String>{
	Customer details= new Customer();

	public final List<String >accountType = Arrays.asList("Savings" , "Salary" , "savings" , "salary");
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		
		return accountType.contains(value);
	}

}
