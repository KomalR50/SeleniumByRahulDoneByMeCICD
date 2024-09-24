package com.selenium.tests;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.TestComponents.BaseTest;
import com.selenium.pageobjects.CartPage;
import com.selenium.pageobjects.CheckoutPage;
import com.selenium.pageobjects.ConfirmationPage;
import com.selenium.pageobjects.LandingPage;
import com.selenium.pageobjects.ProductCatalogue;

public class CommentsSubmitOrderTest extends BaseTest {

	// Use testng annotation instead of java code
	@Test
	public void submitOrderTest() throws IOException {
		String productName = "ZARA COAT 3";
		// this code goes to base class
		//LandingPage landingPage = launchApplication();
// Login
		// landingPage.goTo();
		ProductCatalogue productCatalogue = landingPage.loginApplication("rasalkomal1998@gmail.com", "Kom@l1998");

//Grab all the displayed products into the list
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();

//CART PAGE CODE
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

//Last Page code
//		String confirmMessage = confirmationPage.getConfirmationMessage();
//		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER"));
//		driver.close();
		
		// Last Page code - Updated chatgpt
		String confirmMessage = confirmationPage.getConfirmationMessage();

		// Log the confirmation message for debugging purposes
		System.out.println("Confirmation Message: " + confirmMessage);

		// Assert that the confirmation message matches the expected text
		// Modify the message comparison to be case-insensitive and print helpful message if it fails
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."), "Order confirmation failed! Expected 'THANK YOU FOR THE ORDER' but found '" + confirmMessage + "'");
		}

}