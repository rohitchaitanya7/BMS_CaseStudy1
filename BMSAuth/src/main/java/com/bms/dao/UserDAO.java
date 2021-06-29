package com.bms.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bms.model.CustomerData;

@Repository
public interface UserDAO extends JpaRepository<CustomerData, String> {
	
}
