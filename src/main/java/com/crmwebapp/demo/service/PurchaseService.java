package com.crmwebapp.demo.service;

import java.util.List;

import com.crmwebapp.demo.entity.Purchase;

public interface PurchaseService {

	public List<Purchase> findAll();

	public Purchase findById(Long theId);

	public Purchase save(Purchase thePurchase);

	public void deleteById(Long theId);

	
}
