package com.selenium.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.pageobjects.CartPage;
import com.selenium.pageobjects.OrderPage;

public class AbstractComponents {

    WebDriver driver;

    // Constructor to initialize the driver and page elements
    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Find the cart header for navigation
    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;
    
    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;

    // Wait for any element to become visible on the page
    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Timeout increased to 15 seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }
    //Wait for errormesage
    public void waitForWebElementToAppear(WebElement FindBy) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.visibilityOf(FindBy));
    }

    // Wait for the element to disappear (used for loaders or animations)
    public void waitForElementToDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased to 15 seconds
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Navigate to the Cart page
    public CartPage goToCartPage() {
        cartHeader.click();
        return new CartPage(driver);
    }
    
 // Navigate to the Orders page
    public OrderPage goToOrdersPage() {
        orderHeader.click();
        OrderPage orderPage = new OrderPage(driver);
        return orderPage;
    }
}
