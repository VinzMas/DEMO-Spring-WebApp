package com.crmwebapp.demo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class CustomerTests {

	static Customer customer;
	static Customer customerSameId1;
	static Customer customerSameId2;
	static Customer customerDifferentId;

	static List<Purchase> purchases = new ArrayList<Purchase>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Long sameId=(long) 1;
		Long diffId=(long) 2;
				
		customer=new 
				Customer("Vincenzo", "Massaro", "en-z@hotmail.it", "123 via fasulla", purchases);
		customerSameId1=new 
				Customer("Vincenzo", "Massaro", "en-z@hotmail.it", "123 via fasulla", purchases);
		customerSameId2 = new
				Customer("Vincenzo", "Massaro", "en-z@hotmail.it", "123 via fasulla", purchases);
		customerDifferentId = new
				Customer("Vincenzo", "Massaro", "en-z@hotmail.it", "123 via fasulla", purchases);
		
		customerSameId1.setId(sameId);
		customerSameId2.setId(sameId);
		customerDifferentId.setId(diffId);
	}
	
	@Test
	public void testEquals() {
		assertTrue(customerSameId1.equals(customerSameId2));
		assertFalse(customerSameId1.equals(customerDifferentId));
	}
	
	@Test
	public void testIsJustLike() {
		System.out.println(customerSameId1);
		System.out.println(customerDifferentId);
		
		assertTrue(customerSameId1.isJustLike(customerDifferentId));
		assertTrue(customerSameId2.isJustLike(customerDifferentId));
		assertTrue(customerSameId1.isJustLike(customerSameId2));

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetCompleteNameFailure() {
		String[] expectedFailure = {
				"Pippo",
				"Giuseppe Raimondo Baudo",
				"Giuseppe Raimondo Vittorio Baudo",
		};
		
		for (int i = 0; i < expectedFailure.length; i++) {
			customer.setCompleteName(expectedFailure[i]);
		}
	}
	
	@Test
	public void testSetCompleteNameSuccess() {
		
		String[] expectedSuccess = {
				"Pippo Baudo",
				"   Pippo Baudo", 
				"Pippo Baudo    ", 
				"   Pippo    Baudo   ",
		};
		
		String completeNameSample = "Pippo Baudo";
		
		for (int i = 0; i < expectedSuccess.length; i++) {
			customer.setCompleteName(expectedSuccess[i]);
			assertEquals(completeNameSample,customer.getCompleteName());
		}

	}

}
