package com.crmwebapp.demo.service;

import java.util.List;

import com.crmwebapp.demo.entity.Customer;

public interface CustomerService {

	public List<Customer> findAll();
	
	public Customer findById(Long id);
	
	public Customer save(Customer customer);
	
	public List<Customer> findByEmail(String email);

	public void deleteById(Long id);

	public Boolean isEmailAvailable(Customer theCustomer);

	public List<Customer> findActiveCustomers();

	public List<Customer> findInactiveCustomers();
	
}
