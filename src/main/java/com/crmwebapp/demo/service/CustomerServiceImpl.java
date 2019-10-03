package com.crmwebapp.demo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crmwebapp.demo.dao.CustomerRepository;
import com.crmwebapp.demo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Customer findById(Long theId) {
		Customer theCustomer;
		Optional<Customer> perhapsCustomer = customerRepository.findById(theId);
		
		if (perhapsCustomer.isPresent()) {
			theCustomer = perhapsCustomer.get();
		} else {
			throw new RuntimeException("id customer '" + theId + "' not foundend");
		}
		
		return theCustomer;
	}

	@Override
	public List<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Boolean isEmailAvailable(Customer theCustomer) {
		Long theCustomerId = theCustomer.getId();
		Boolean emailIsAvailable = true;	
	
		List<Customer> customersWithThisEmail = findByEmail(theCustomer.getEmail());	
		if (CollectionUtils.isNotEmpty(customersWithThisEmail)) {
			Iterator<Customer> iterator = customersWithThisEmail.iterator();
			do {
				Customer tempCustomer = (Customer) iterator.next();	
	
				boolean tempCustomerIsTheCustomer = tempCustomer.getId().equals(theCustomerId);
				if(!tempCustomerIsTheCustomer) emailIsAvailable = false;
			} while(iterator.hasNext() && emailIsAvailable);
		}
		return emailIsAvailable;
	}

	@Override
	public List<Customer> findActiveCustomers() {
		return customerRepository.findCustomersByIsActive(true);
	}

	@Override
	public List<Customer> findInactiveCustomers() {
		return customerRepository.findCustomersByIsActive(false);
	}
}
