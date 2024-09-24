package com.selenium.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.module.Input;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.TestComponents.BaseTest;
import com.selenium.pageobjects.CartPage;
import com.selenium.pageobjects.CheckoutPage;
import com.selenium.pageobjects.ConfirmationPage;
import com.selenium.pageobjects.LandingPage;
import com.selenium.pageobjects.OrderPage;
import com.selenium.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";
	// Use testng annotation instead of java code
	@Test(dataProvider = "getData",groups= {"Purchase"})
	public void submitOrderTest(HashMap<String,String>input) throws IOException {
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		// Last Page code - Updated chatgpt
		String confirmMessage = confirmationPage.getConfirmationMessage();

		// Log the confirmation message for debugging purposes
		System.out.println("Confirmation Message: " + confirmMessage);

		// Assert that the confirmation message matches the expected text
		// Modify the message comparison to be case-insensitive and print helpful message if it fails
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."), "Order confirmation failed! Expected 'THANK YOU FOR THE ORDER' but found '" + confirmMessage + "'");
		}
	
	//To Verify that ZARA COAT 3 is displaying in orders page
	@Test(dependsOnMethods = {"submitOrderTest"})
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("rasalkomal1998@gmail.com", "Kom@l1998");
		productCatalogue.goToOrdersPage();
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

		//using json formate external file
	    List<HashMap<String,String>> data =	getJsonDataToMap("E:\\KOMAL\\Recordings\\a\\Eclipse-Workspace2\\SeleniumFrameworkDesign\\src\\test\\java\\com\\selenium\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1) } };
		
		/*OR
		   Previous method
		   @DataProvider
	       public Object[][] getData() {
		   HashMap<String,String> map = new HashMap<String,String>();
		    return new Object[][] {{"rasalkomal1998@gmail.com","Kom@l1998","ZARA COAT 3"},{"komalrasal875@gmail.com","ADIDAS ORIGINAL"};
		 * */
		
		//OR
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email","rasalkomal1998@gmail.com");
//		map.put("password","Kom@l1998");
//		map.put("product","ZARA COAT 3");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email","komalrasal875@gmail.com");
//		map1.put("password","Kom@l1998");
//		map1.put("product","ADIDAS ORIGINAL");

	}
}