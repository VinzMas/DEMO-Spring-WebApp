package com.crmwebapp.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crmwebapp.demo.dao.ProductRepository;
import com.crmwebapp.demo.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAllByOrderByNameAsc();
	}

	@Override
	public Product findById(Long theId) {
		Product theProduct;
		Optional<Product> perhapsProduct = productRepository.findById(theId);

		if (perhapsProduct.isPresent()) {
			theProduct = perhapsProduct.get();
		} else {
			throw new RuntimeException("id product '" + theId + "' not foundend");
		}

		return theProduct;
	}

	@Override
	public Product save(Product theProduct) {
		return productRepository.save(theProduct);
	}

	@Override
	public void deleteById(Long theId) {
		productRepository.deleteById(theId);
	}
	
	@Override
	public List<Product> findActiveProducts() {
		return productRepository.findProductsByIsActive(true);
	}

	@Override
	public List<Product> findInactiveProducts() {
		return productRepository.findProductsByIsActive(false);
	}
}
