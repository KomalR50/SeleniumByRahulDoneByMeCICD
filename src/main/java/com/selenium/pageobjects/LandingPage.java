package com.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.selenium.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{

	WebDriver driver;
	public LandingPage(WebDriver driver) {
	
		super(driver);
		//Initialization
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	// OR PageFactory
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	//.ng-tns-c4-5.ng-star-inserted.ng-trigger.ng-trigger-flyInOut.ngx-toastr.toast-error
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;

	//Action method
	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	//Action method2 
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	//Action MEthod
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
	    return errorMessage.getText();
	}

}
