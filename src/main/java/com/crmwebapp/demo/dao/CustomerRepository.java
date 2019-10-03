package com.crmwebapp.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crmwebapp.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public List<Customer> findAllByOrderByLastNameAsc();
	public List<Customer> findByEmail(String email);
	public List<Customer> findCustomersByIsActive(boolean activeStatus);
	
}
