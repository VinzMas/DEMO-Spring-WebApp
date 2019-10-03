package com.crmwebapp.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crmwebapp.demo.entity.Customer;
import com.crmwebapp.demo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private List<Customer> customers;

	@Autowired
	private CustomerService customerService;


	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		customers = customerService.findAll();
		theModel.addAttribute("customers", customers);

		return "customers/list-customers";
	}
	
	@GetMapping("/list/active")
	public String listActiveCustomers(Model theModel) {
		customers = customerService.findActiveCustomers();
		theModel.addAttribute("customers", customers);
						
		return "customers/list-customers";
	}

	@GetMapping("/list/inactive")
	public String listInactiveCustomers(Model theModel) {
		customers = customerService.findInactiveCustomers();
		theModel.addAttribute("customers", customers);
						
		return "customers/list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);

		return "customers/customer-form";
	}

	@GetMapping("/update")
	public String update(@RequestParam("customerId") Long theId, @RequestParam(value="isActive", required=false) Boolean isActiveStatus, Model theModel) {
		Customer theCustomer = customerService.findById(theId);
				
		if (isAChangeStatusRequest(isActiveStatus)) {
			theCustomer.setIsActive(isActiveStatus);
			customerService.save(theCustomer);
			return "redirect:/customer/list";
		}
		else {
			theModel.addAttribute("customer", theCustomer);
			return "customers/customer-form";
		}
		
	}

	private boolean isAChangeStatusRequest(Boolean isActiveStatus) {
		return isActiveStatus != null;
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("customer") Customer theCustomer, BindingResult theBindingResult) {

		ifMoreConstraintsViolationAddErrors(theCustomer, theBindingResult);

		if (theBindingResult.hasErrors()) {
			return "customers/customer-form";
		} else {
			customerService.save(theCustomer);
			return "redirect:/customer/list";
		}
	}

	private void ifMoreConstraintsViolationAddErrors(Customer theCustomer, BindingResult theBindingResult) {		
		if (!customerService.isEmailAvailable(theCustomer)) {
			addEmailError(theCustomer, theBindingResult);
		}
	}

	private void addEmailError(Customer theCustomer, BindingResult theBindingResult) {
		String errorMsg = StringUtils.wrap(theCustomer.getEmail(),'\"') + " unavailable, please chose another email address";
		FieldError fieldError = new FieldError("customer", "email", errorMsg);
		theBindingResult.addError(fieldError);
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") Long theId) {
		customerService.deleteById(theId);
		return "redirect:/customer/list";
	}
}
