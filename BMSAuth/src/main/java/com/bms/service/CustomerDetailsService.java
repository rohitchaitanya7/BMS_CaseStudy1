package com.bms.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bms.dao.UserDAO;
import com.bms.exception.UnauthorizedException;
import com.bms.model.CustomerData;

/**Service class*/
@Service
public class CustomerDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userdao;

	/**
	 * @param String
	 * @return User 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uname) {
		
		try
		{
			
			Optional<CustomerData> user=userdao.findById(uname);
			if(user.isPresent()) {
				user.get().getUsername();
				return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
			}
			else {
				throw new UsernameNotFoundException("User id not found");
			}
		}
		catch (Exception e) {
			throw new UnauthorizedException("UsernameNotFoundException");
		}	
		
		
	}

}
