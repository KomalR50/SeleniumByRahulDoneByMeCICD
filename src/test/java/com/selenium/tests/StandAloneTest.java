package com.selenium.tests;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.selenium.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		//WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\komal.rasal\\Downloads\\chromedriver-win64\\chromedriver-win64/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);

		// Login
		driver.findElement(By.id("userEmail")).sendKeys("rasalkomal1998@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Kom@l1998");
		driver.findElement(By.id("login")).click();
		
		//Grab all the displayed products into the list
		//put wait into GLobal level instead for certain operation only
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//Code to wait till toast msg appeared on screen after adding into cart
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//Code to wait until screen load with process bar
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		//OR
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //too improve performance
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//CART PAGE CODE
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	    Boolean match =	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		//driver.findElement(By.cssSelector(".totalRow button")).click();
		 WebElement button = driver.findElement(By.cssSelector(".totalRow button"));
		// wait.until(d -> button.isDisplayed());
		 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", button);


		 //Dropdown handle code(Payment screen)
		 Actions a = new Actions(driver);
		 driver.findElement(By.cssSelector("[placeholder='Select Country']")).click();
	        WebElement countryInput = driver.findElement(By.cssSelector("[placeholder='Select Country']"));
	        a.click(countryInput).sendKeys("India").build().perform();
	        // Debugging step: Print all available options to check if they are loaded
	        try {
	            Thread.sleep(5000); // Adjust delay if needed
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Wait for the dropdown results to be visible
	        WebElement dropdownResults = null;
	        try {
	            dropdownResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	            System.out.println("Dropdown results are visible.");
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }

	        if (dropdownResults != null) {
	            // Select the desired option from the dropdown
	            WebElement countryOption = driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]"));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countryOption);
	            countryOption.click();

	            // Scroll into view and click the submit button
	            WebElement submitButton = driver.findElement(By.cssSelector(".action__submit"));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
	            // Wait for a short time to ensure the element is clickable
	            try {
	                Thread.sleep(500); // Short wait to ensure the page is fully rendered
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            submitButton.click();
	        
	     //Last Page code
         String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	     Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER"));
	     driver.close();
	        }		 	

}}