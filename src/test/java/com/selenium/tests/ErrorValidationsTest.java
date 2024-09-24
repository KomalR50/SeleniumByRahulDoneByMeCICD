package com.selenium.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.selenium.TestComponents.BaseTest;
import com.selenium.TestComponents.Retry;
import com.selenium.pageobjects.CartPage;
import com.selenium.pageobjects.ProductCatalogue;
public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer = Retry.class)
	public void LoginErrorValidations() throws IOException {
		//String productName = "ZARA COAT 3";
	    landingPage.loginApplication("rasalkomal1998@gmail.com", "Kom@l1988");
		landingPage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
		System.out.println("You have provided " + landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidations() throws IOException {
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("komalrasal875@gmail.com", "Kom@l1998");
		List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay("CARA COAT 33");
		Assert.assertFalse(match);
	}
}