package com.crmwebapp.demo.service;

import java.util.List;

import com.crmwebapp.demo.entity.Product;

public interface ProductService {

	public List<Product> findAll();

	public Product findById(Long theId);

	public Product save(Product theProduct);

	public void deleteById(Long theId);

	public List<Product> findActiveProducts();

	public List<Product> findInactiveProducts();

	
}
