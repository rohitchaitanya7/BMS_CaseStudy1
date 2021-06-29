package com.bms.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.model.Customer;
@Repository
public interface CustomerDao extends JpaRepository<Customer,String> {

}
