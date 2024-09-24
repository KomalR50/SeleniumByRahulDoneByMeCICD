package com.selenium.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Wait for up to 30 seconds
        PageFactory.initElements(driver, this);
    }

    // Web elements
    @FindBy(css = ".action__submit")
    WebElement submit;

    @FindBy(css = "[placeholder='Select Country']")
    WebElement country;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
    WebElement selectCountry;

    By results = By.cssSelector(".ta-results");

    // Action method to select a country
    public void selectCountry(String countryName) {
        Actions a = new Actions(driver);

        // Click on the country input field
        wait.until(ExpectedConditions.elementToBeClickable(country)).click();

        // Type the country name in the input field
        a.click(country).sendKeys(countryName).build().perform();

        // Wait for the dropdown results to appear
        WebElement dropdownResults = wait.until(ExpectedConditions.visibilityOfElementLocated(results));
        System.out.println("Dropdown results are visible.");

        // Select the desired country option from the dropdown
        wait.until(ExpectedConditions.elementToBeClickable(selectCountry));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectCountry);
        selectCountry.click();

        // Wait for a short time to ensure the element is clickable (if necessary)
        waitForElementToAppear(By.cssSelector(".action__submit"));
    }

    // Method to submit the order
    public ConfirmationPage submitOrder() {
        // Wait for the submit button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(submit));

        // Scroll the submit button into view and ensure it's clickable
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
            wait.until(ExpectedConditions.elementToBeClickable(submit));

            // Attempt to click the submit button
            submit.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element is not clickable, trying JavaScript click...");

            // If element is intercepted, use JavaScript to click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        return new ConfirmationPage(driver);
    }
}
