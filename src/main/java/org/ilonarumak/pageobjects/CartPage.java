package org.ilonarumak.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractPage {
    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private final By itemsListLocator = By.cssSelector("li.items");
    private final By itemNameLocator = By.cssSelector(".cartSection h3");
//    private final By itemTotalPriceLocator = By.cssSelector(".prodTotal p");
//    private final By cartSubTotalPriceLocator = By.cssSelector(".totalRow:first-child span[class='value']");
//    private final By cartTotalPriceLocator = By.cssSelector(".totalRow:nth-child(2) span[class='value']");

    @FindBy(css = ".totalRow:last-child button")
    WebElement checkoutButton;

    public List<WebElement> getItemsList() {
        return driver.findElements(itemsListLocator);
    }

    public WebElement getItem(String itemName) {
        return getItemsList().stream().filter(item ->
                getItemName(item).equalsIgnoreCase(itemName)).findFirst().orElse(null);
    }


    public String getItemName(WebElement element) {
        return element.findElement(itemNameLocator).getText();
    }
//
//    public String getItemTotalPrice(WebElement element) {
//        return element.findElement(itemTotalPriceLocator).getText().substring(1).trim();
//    }
//
//    public String getCartSubTotalPrice() {
//        return driver.findElement(cartSubTotalPriceLocator).getText().substring(1).trim();
//    }
//
//    public String getCartTotalPrice() {
//        return driver.findElement(cartTotalPriceLocator).getText().substring(1).trim();
//    }

    public CheckoutPage clickCheckoutButton() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}
