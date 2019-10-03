package com.crmwebapp.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crmwebapp.demo.dao.PurchaseRepository;
import com.crmwebapp.demo.entity.Purchase;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	public List<Purchase> findAll() {
		return purchaseRepository.findAllByOrderByDateTimeDesc();
	}

	@Override
	public Purchase findById(Long theId) {
		Purchase thePurchase;
		Optional<Purchase> perhapsPurchase = purchaseRepository.findById(theId);
		
		if (perhapsPurchase.isPresent()) {
			thePurchase = perhapsPurchase.get();
		} else {
			throw new RuntimeException("id purchase '" + theId + "' not foundend");
		}
		
		return thePurchase;
	}

	@Override
	public Purchase save(Purchase thePurchase) {
		return purchaseRepository.save(thePurchase);
	}

	@Override
	public void deleteById(Long theId) {
		purchaseRepository.deleteById(theId);
	}

}
