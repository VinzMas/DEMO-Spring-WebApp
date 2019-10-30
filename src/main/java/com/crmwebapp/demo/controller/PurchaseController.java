package com.crmwebapp.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crmwebapp.demo.entity.Customer;
import com.crmwebapp.demo.entity.Product;
import com.crmwebapp.demo.entity.Purchase;
import com.crmwebapp.demo.service.CustomerService;
import com.crmwebapp.demo.service.ProductService;
import com.crmwebapp.demo.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

	private List<Purchase> purchases;

	@Autowired private PurchaseService purchaseService;
	@Autowired private CustomerService customerService;
	@Autowired private ProductService productService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String listPurchases(Model theModel) {
		purchases = purchaseService.findAll();
		theModel.addAttribute("purchases", purchases);
		
		return "purchases/list-purchases";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Purchase thePurchase = new Purchase();
		theModel.addAttribute("purchase", thePurchase);

		provideCustomers(theModel);
		provideProducts(theModel);

		theModel.addAttribute("isAnAdd", true);
		
		return "purchases/purchase-form";
	}

	private void provideCustomers(Model theModel) {
		List<Customer> customers = customerService.findAll();
		theModel.addAttribute("customers", customers);
	}

	private void provideProducts(Model theModel) {
		List<Product> products = productService.findAll();
		theModel.addAttribute("products", products);
	}

	//TODO: considerare di rinominare anche questo come quelli degli altri controller
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("purchaseId") Long theId, Model theModel) {
		Purchase thePurchase = purchaseService.findById(theId);
		theModel.addAttribute("purchase", thePurchase);
		provideProducts(theModel);
		theModel.addAttribute("isAnAdd", false);
		
		return "purchases/purchase-form";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("purchase") Purchase thePurchase, BindingResult theBindingResult, Model theModel) {
		thePurchase.calcAndSetTotalSpending();
		
		theModel.addAttribute("isAnAdd", isAnAdd(thePurchase));
		
		if (theBindingResult.hasErrors()) {
			ifIsAnAddProvideCustomers(theModel, thePurchase);
			provideProducts(theModel);
			return "purchases/purchase-form";		
		} else {
			purchaseService.save(thePurchase);
			return "redirect:/purchase/list";
		}
	}



	private void ifIsAnAddProvideCustomers(Model theModel, Purchase thePurchase) {
		if (isAnAdd(thePurchase)) {
			provideCustomers(theModel);
		}
	}

	private boolean isAnAdd(Purchase thePurchase) {
		return thePurchase.getId() == null;
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("purchaseId") Long theId) {
		purchaseService.deleteById(theId);
		return "redirect:/purchase/list";
	}

}
