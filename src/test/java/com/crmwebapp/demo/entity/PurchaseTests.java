package com.crmwebapp.demo.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.crmwebapp.demo.PaymentMethod;


@RunWith(SpringRunner.class)
public class PurchaseTests {

	static Purchase mainPurchase;
	static Purchase purchaseSameIdDiffBusinessKey;
	static Purchase purchaseDiffIdDiffBusinessKey;
	static Purchase purchaseAllTheSame;
	static Purchase purchaseAllTheSameExcId;
	static Purchase purchaseSameBusinessKey;
	static Purchase purchaseDiffBusinessKey1;
	static Purchase purchaseDiffBusinessKey2;
	static Purchase purchaseDiffBusinessKey3;

	static Product product1 = Mockito.mock(Product.class);
	static Product product2 = Mockito.mock(Product.class);
	static Customer customer1 = Mockito.mock(Customer.class);
	static Customer customer2 = Mockito.mock(Customer.class);
	static List<Product> products1 = new ArrayList<Product>();
	static List<Product> products2 = new ArrayList<Product>();


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {			
		products1.add(product1);
		products2.add(product2);

		initializePurchases();
	}

	@Test
	public void testEquals() {
		assertFalse(mainPurchase.equals(purchaseSameIdDiffBusinessKey));
		assertFalse(mainPurchase.equals(purchaseDiffIdDiffBusinessKey));
		assertTrue(mainPurchase.equals(purchaseAllTheSame));
		assertTrue(mainPurchase.equals(purchaseAllTheSameExcId));
		assertTrue(mainPurchase.equals(purchaseSameBusinessKey));
		assertFalse(mainPurchase.equals(purchaseDiffBusinessKey1));
		assertFalse(mainPurchase.equals(purchaseDiffBusinessKey2));
		assertFalse(mainPurchase.equals(purchaseDiffBusinessKey3));

	}


	@Test
	public void testIsJustLike() {
		assertFalse(mainPurchase.isJustLike(purchaseSameIdDiffBusinessKey));
		assertFalse(mainPurchase.isJustLike(purchaseDiffIdDiffBusinessKey));
		assertTrue(mainPurchase.isJustLike(purchaseAllTheSame));
		assertTrue(mainPurchase.isJustLike(purchaseAllTheSameExcId));
		assertFalse(mainPurchase.isJustLike(purchaseSameBusinessKey));
		assertFalse(mainPurchase.isJustLike(purchaseDiffBusinessKey1));
		assertFalse(mainPurchase.isJustLike(purchaseDiffBusinessKey2));
		assertFalse(mainPurchase.isJustLike(purchaseDiffBusinessKey3));

	}


	private static void initializePurchases() throws InterruptedException {
		createPurchasesWithDelay();
		setPurchasesAppropriately();
	}

	private static void createPurchasesWithDelay() throws InterruptedException {

		mainPurchase=new Purchase(PaymentMethod.CASH, customer1, products1);
		sleep();

		purchaseSameIdDiffBusinessKey = new Purchase(PaymentMethod.CASH, customer1, products1);
		sleep();

		purchaseDiffIdDiffBusinessKey = new Purchase(PaymentMethod.CASH, customer1, products1);
		sleep();

		purchaseAllTheSame=new Purchase(PaymentMethod.CASH, customer1, products1);
		sleep();

		purchaseAllTheSameExcId=new Purchase(PaymentMethod.CASH, customer1, products1);
		sleep();

		purchaseSameBusinessKey = new Purchase(PaymentMethod.AMAZON_PAY, customer1, products1);
		sleep();

		purchaseDiffBusinessKey1=new Purchase(PaymentMethod.CASH, customer1, products1); 
		sleep();

		purchaseDiffBusinessKey2=new Purchase(PaymentMethod.CASH, customer2, products1);
		sleep();

		purchaseDiffBusinessKey3=new Purchase(PaymentMethod.CASH, customer1, products2);
		sleep();

	}

	private static void sleep() throws InterruptedException {
		long theDelay=500000;
		for (long i = 0; i < theDelay; i++);
	}

	private static void setPurchasesAppropriately() {
		Long theId=(long) 3;

		mainPurchase.setId(theId);
		purchaseSameIdDiffBusinessKey.setId(theId);
		purchaseSameBusinessKey.setDateTime(mainPurchase.getDateTime());
		purchaseAllTheSame.setDateTime(mainPurchase.getDateTime());
		purchaseAllTheSame.setId(theId);
		purchaseAllTheSameExcId.setDateTime(mainPurchase.getDateTime());
	}
}


