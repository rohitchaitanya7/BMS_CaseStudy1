package com.bms.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bms.exception.InitialDepositException;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.model.Customer;
import com.bms.model.ResponseForSuccess;
import com.bms.service.RegisterService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@RestController
public class BMSRegisterController {

	@Autowired
	private RegisterService registerService;
	
	@PostMapping(value = "/customer/register")
	public ResponseEntity<ResponseForSuccess> register(@Valid @RequestBody Customer customer) throws UnauthorizedException,InitialDepositException{
		
		return registerService.register(customer);
	}
	
	@PutMapping(value = "/customer/{customer_id}/updateDetails")
	public ResponseEntity<ResponseForSuccess> editDetails(@RequestHeader("Authorization") String token,@Valid @RequestBody Customer customer,@PathVariable String customer_id) throws InvalidTokenException,UnauthorizedException{
			return registerService.editDetails(token,customer,customer_id);
	}

	@GetMapping(value = "/customer/{customer_id}/getDetails")
	public ResponseEntity<Object> getUser(@PathVariable String customer_id){
		System.out.println(customer_id);	
		return registerService.getCustomerDetails(customer_id);
	}
	

	
	
}
