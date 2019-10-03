package com.crmwebapp.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crmwebapp.demo.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	public List<Purchase> findAllByOrderByDateTimeDesc();
	
}
