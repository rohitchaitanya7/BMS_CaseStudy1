package com.bms.controller;

import java.util.Base64;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bms.dao.UserDAO;
import com.bms.model.AuthResponse;
import com.bms.model.CustomerData;
import com.bms.model.LoginDetails;
import com.bms.service.CustomerDetailsService;
import com.bms.service.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author kumar
 *
 */
@RestController
@Slf4j
@Api(produces = "application/json", value = "Creating and validating the Jwt token")
public class AuthController {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private CustomerDetailsService custdetailservice;
	@Autowired
	private UserDAO userservice;

	/**
	 * 
	 * @param userlogincredentials
	 * @return
	 */
	@ApiOperation(value = "Verify credentials and generate JWT Token", response = ResponseEntity.class)
	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody LoginDetails userlogincredentials) {
		//Generates token for login
		byte[] actualByte= Base64.getDecoder().decode(userlogincredentials.getUsername());
		userlogincredentials.setUsername(new String(actualByte));
		actualByte= Base64.getDecoder().decode(userlogincredentials.getPassword());
		userlogincredentials.setPassword(new String(actualByte));
		final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUsername());
		String uid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(userlogincredentials.getPassword())) {
			uid = userlogincredentials.getUsername();
			generateToken = jwtutil.generateToken(userdetails);
			log.info("login successful");
			return new ResponseEntity<>(new CustomerData(uid, null, generateToken), HttpStatus.OK);
		} else {
			log.info("At Login : ");
			log.error("Not Accesible");
			return new ResponseEntity<>("Not Accesible", HttpStatus.FORBIDDEN);
		}
	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Validate JWT Token", response = ResponseEntity.class)
	@GetMapping(value = "/validate")
	public ResponseEntity<Object> getValidity(@RequestHeader("Authorization") final String token) {
		//Returns response after Validating received token
		String token1 = token.substring(7);
		AuthResponse res = new AuthResponse();
		if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
			res.setUsername(jwtutil.extractUsername(token1));
			res.setValid(true);
			Optional<CustomerData> user1=userservice.findById(jwtutil.extractUsername(token1));
			if(user1.isPresent()) {
				res.setUsername(user1.get().getUsername());
				res.setMessage("token successfully validated");
				log.info("token successfully validated");
			}
		} else {
			res.setValid(false);
			res.setMessage("Invalid Token Received");
			log.info("At Validity : ");
			log.error("Token Has Expired");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}