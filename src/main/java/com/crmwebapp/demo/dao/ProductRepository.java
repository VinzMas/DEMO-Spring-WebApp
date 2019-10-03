package com.crmwebapp.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crmwebapp.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findAllByOrderByNameAsc();
	public List<Product> findProductsByIsActive(boolean activeStatus);
}
