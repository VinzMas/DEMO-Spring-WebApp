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

import com.crmwebapp.demo.entity.Product;
import com.crmwebapp.demo.entity.Purchase;
import com.crmwebapp.demo.service.ProductService;
import com.crmwebapp.demo.service.PurchaseService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private List<Product> products;
	
	@Autowired private ProductService productService;
	@Autowired private PurchaseService purchaseService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/list")
	public String listProducts(Model theModel) {
		products = productService.findAll();
		theModel.addAttribute("products", products);
						
		return "products/list-products";
	}
	
	@GetMapping("/list/active")
	public String listActiveProducts(Model theModel) {
		products = productService.findActiveProducts();
		theModel.addAttribute("products", products);
						
		return "products/list-products";
	}

	@GetMapping("/list/inactive")
	public String listInactiveProducts(Model theModel) {
		products = productService.findInactiveProducts();
		theModel.addAttribute("products", products);
						
		return "products/list-products";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Product theProduct = new Product();
		theModel.addAttribute("product", theProduct);
		
		return "products/product-form";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("productId") Long theId, @RequestParam(value="isActive", required=false) Boolean isActiveStatus, Model theModel) {
		Product theProduct = productService.findById(theId);
		
		if (isAChangeStatusRequest(isActiveStatus)) {
			theProduct.setIsActive(isActiveStatus);
			productService.save(theProduct);
			return "redirect:/product/list";
		}
		else {
			theModel.addAttribute("product", theProduct);
			return "products/product-form";
		}
	}

	private boolean isAChangeStatusRequest(Boolean isActiveStatus) {
		return isActiveStatus != null;
	}
	
	@GetMapping("/showPurchaseProducts")
	public String showPurchaseProducts(@RequestParam("purchaseId") Long theId, Model theModel) {
		Purchase thePurchase = purchaseService.findById(theId);
		List<Product> thePurchaseProducts = thePurchase.getProducts();
		
		theModel.addAttribute("products", thePurchaseProducts);
		
		return "products/list-products";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("product") Product theProduct, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "products/product-form";
		
		} else {
			productService.save(theProduct);
			return "redirect:/product/list";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("productId") Long theId) {
		productService.deleteById(theId);
		return "redirect:/product/list";
	}
}
