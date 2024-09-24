package com.selenium.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import com.selenium.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {

    WebDriver driver;

    @FindBy(css = ".totalRow button")
    WebElement checkoutEle;

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartProducts;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean VerifyProductDisplay(String productName) {
        Boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
        return match;
    }

    public CheckoutPage goToCheckout() {
        // FluentWait to ensure that the checkout button is displayed
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(2))
            .ignoring(Exception.class);

        // Wait until the checkout button is displayed
        wait.until(d -> checkoutEle.isDisplayed());

        // Use JavascriptExecutor to scroll into view and click the button
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", checkoutEle);
        
        return new CheckoutPage(driver);
    }
}
