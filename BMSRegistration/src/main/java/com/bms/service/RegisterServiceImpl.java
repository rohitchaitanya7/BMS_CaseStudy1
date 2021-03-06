package com.bms.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.dao.CustomerDao;
import com.bms.dao.CustomerDataDao;
import com.bms.exception.InitialDepositException;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.model.Customer;
import com.bms.model.CustomerData;
import com.bms.model.ResponseForSuccess;
import com.bms.restclients.AuthFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RegisterServiceImpl implements RegisterService {

	
	@Autowired
	private CustomerDao cd;
	
	@Autowired
	private CustomerDataDao cdao;
	
	@Autowired
	private AuthFeign authFeign;
	
	public int getAge(Date dob) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	      formatter.format(dob);
	      Instant instant = dob.toInstant();
	      ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
	      LocalDate givenDate = zone.toLocalDate();
	      Period period = Period.between(givenDate, LocalDate.now());
	      return period.getYears();
	}
	@Override
	public ResponseEntity<ResponseForSuccess> register(Customer customer) throws InitialDepositException,UnauthorizedException{
		
			Optional<CustomerData> user=cdao.findById(customer.getLoginDetails().getUsername());
			if(user.isPresent()) {
				throw new UnauthorizedException("User already Exists");
			}
			else {
				customer.setCustomer_id("R-"+ (101 + cd.count()) );
				
				Date dob=customer.getDob();
				int age=getAge(dob);
				if(age<18) {
					customer.setCitizenStatus("Minor");
				}
				else if(age<=60){
					customer.setCitizenStatus("Normal");
				}
				else {
					customer.setCitizenStatus("Senior");
				}
				if(customer.getAccountType().equalsIgnoreCase("Savings")) {
					if(customer.getDepositAmount()<5000)
						throw new InitialDepositException("Initial Amount should be more than 5000");
				}
				customer.setRegistrationDate(new Date());
				cd.save(customer);
				return new ResponseEntity<>(new ResponseForSuccess("User Registered Successfully ",customer.getCustomer_id(),"/customer/"+customer.getCustomer_id()+"/getDetails"), HttpStatus.OK);
			}
		}
	@HystrixCommand(fallbackMethod = "serviceDown")
	@Override
	public ResponseEntity<ResponseForSuccess> editDetails(String token,Customer customer,String cid) throws UnauthorizedException,InvalidTokenException{
		
		if (authFeign.getValidity(token).getBody().isValid()) {
				Optional<Customer> cust=cd.findById(cid);
				if(cust.isPresent()) {
					customer.setRegistrationDate(cust.get().getRegistrationDate());
					customer.setCitizenStatus(cust.get().getCitizenStatus());
					customer.setCustomer_id(cid);
					cd.save(customer);
					return new ResponseEntity<>(new ResponseForSuccess("Customer Details Updated Successfully",customer.getCustomer_id(),"/customer/"+customer.getCustomer_id()+"/getDetails"), HttpStatus.OK);
				}
				else {
					throw new UnauthorizedException("User Not Found");
				}
		}
		else {
			throw new InvalidTokenException("Token not valid");
		}
	}
	@Override
	public ResponseEntity<Object> getCustomerDetails(String cid) {
		Optional<Customer> cust=cd.findById(cid);
		if(cust.isPresent()) {
			return new ResponseEntity<>(cust.get(),HttpStatus.OK);
		}
		else {
			throw new UnauthorizedException("User Not Found");
		}
	}
	
	public ResponseEntity<ResponseForSuccess> serviceDown(String token,Customer customer,String cid) {
		return new ResponseEntity<>(new ResponseForSuccess("Service Down Try after some time",null,"/customer/register"),HttpStatus.GATEWAY_TIMEOUT);
	}

}

