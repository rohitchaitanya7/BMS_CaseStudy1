package com.bms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.model.CustomerData;

public interface CustomerDataDao extends JpaRepository<CustomerData, String> {

}
