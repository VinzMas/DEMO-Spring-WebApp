package com.crmwebapp.demo.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class ProductTests {

	static Product productSameId1;
	static Product productSameId2;
	static Product productDifferentId;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Long sameId=(long) 1;
		Long diffId=(long) 2;
				
		productSameId1=new 
				Product("iPhone", "Telefonia", (float) 1100.00, "Smartphone di ultima generazione Apple", 100);
		productSameId2 = new
				Product("iPhone", "Telefonia", (float) 1100.00, "Smartphone di ultima generazione Apple", 100);
		productDifferentId = new
				Product("iPhone", "Telefonia", (float) 1100.00, "Smartphone di ultima generazione Apple", 100);
		
		productSameId1.setId(sameId);
		productSameId2.setId(sameId);
		productDifferentId.setId(diffId);
	}
	
	@Test
	public void testEquals() {
		assertTrue(productSameId1.equals(productSameId2));
		assertFalse(productSameId1.equals(productDifferentId));
	}
	
	@Test
	public void testIsJustLike() {
		assertTrue(productSameId1.isJustLike(productSameId2));
		assertTrue(productSameId1.isJustLike(productDifferentId));
		assertTrue(productSameId2.isJustLike(productDifferentId));
		
	}

}
