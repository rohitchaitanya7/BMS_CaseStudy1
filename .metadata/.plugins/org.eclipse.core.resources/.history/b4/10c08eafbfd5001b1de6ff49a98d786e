package com.cognizant.mailorderpharmacy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.mailorderpharmacy.dao.UserDAO;
import com.cognizant.mailorderpharmacy.model.UserData;


@SpringBootTest
 class ServiceTest {

	UserDetails userdetails;
	
	@InjectMocks
	CustomerDetailsService custdetailservice;

	@Mock
	UserDAO userservice;

	@Test
	 void loadUserByUsernameTest() {
		
		UserData user=new UserData("kumar","kumar","kumar",null);
		Optional<UserData> data =Optional.of(user) ;
		when(userservice.findById("kumar")).thenReturn(data);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername("kumar");
		assertEquals(user.getUserid(),loadUserByUsername.getUsername());
	}
	@Test
	 void loadUserByUsernameFalseTest() {
		
		UserData user=new UserData("kumar","kumar","kumar",null);
		Optional<UserData> data =Optional.of(user) ;
		when(userservice.findById("kumar")).thenReturn(data);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUserid());
		assertNotEquals(user.getUserid()+"false",loadUserByUsername.getUsername());
	}

	
}
