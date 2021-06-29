package com.cognizant.mailorderpharmacy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.mailorderpharmacy.dao.UserDAO;
import com.cognizant.mailorderpharmacy.model.AuthResponse;
import com.cognizant.mailorderpharmacy.model.UserData;
import com.cognizant.mailorderpharmacy.service.CustomerDetailsService;
import com.cognizant.mailorderpharmacy.service.JwtUtil;

@SpringBootTest
class AuthControllerTest {

	@InjectMocks
	AuthController authController;

	AuthResponse authResponse;

	UserDetails userdetails;

	@Mock
	JwtUtil jwtutil;

	@Mock
	CustomerDetailsService custdetailservice;

	@Mock
	UserDAO userservice;

	@Test
	 void loginTest() {

		UserData user = new UserData("kumar", "kumar",null,null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUserid());
		UserDetails value = new User(user.getUserid(), user.getUpassword(), new ArrayList<>());
		when(custdetailservice.loadUserByUsername(user.getUserid())).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		assertEquals( 200, login.getStatusCodeValue());
	}

	@Test
	 void loginTestFailed() {

		UserData user = new UserData("kumar", "kumar",null,null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUserid());
		UserDetails value = new User(user.getUserid(), user.getUpassword()+"wrong", new ArrayList<>());
		when(custdetailservice.loadUserByUsername(user.getUserid())).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		assertEquals( 403, login.getStatusCodeValue());
	}

	@Test
	void registerTest() {
		UserData user = new UserData("zawse", "asedr","uytre",null);
		ResponseEntity<?> register = authController.register(user);
		assertEquals( 200, register.getStatusCodeValue());
	}
	
	@Test
	void registerNegativeTest() {
		UserData user = new UserData("kumar", "kumar",null,null);
		Optional<UserData> data = Optional.of(user);
		when(userservice.findById("kumar")).thenReturn(data);
		ResponseEntity<?> register = authController.register(user);
		assertEquals( 409, register.getStatusCodeValue());
	}
	
	@Test
	 void validateTestValidtoken() {

		
		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("kumar");
		UserData user = new UserData("kumar", "kumar", "kumar",null);
		Optional<UserData> data = Optional.of(user);
		when(userservice.findById("kumar")).thenReturn(data);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("true"));

	}

	@Test
	 void validateTestInValidtoken() {

		
		when(jwtutil.validateToken("token")).thenReturn(false);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("false"));
	}

	

}
