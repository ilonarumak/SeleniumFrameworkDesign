package org.ilonarumak.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrderConfirmationPage extends AbstractPage {
    private final WebDriver driver;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private final By orderItemListLocator = By.cssSelector("table.order-summary div tr");
    private final By orderItemName = By.cssSelector("td:nth-child(2) div.title");

    @FindBy(css = "h1.hero-primary")
    WebElement confirmationMessage;

    @FindBy(css = "label[class='ng-star-inserted']")
    WebElement orderNumber;

    public String getConfirmationMessage() {
        return confirmationMessage.getText().trim();
    }

    public List<WebElement> getOrderItemList() {
        return driver.findElements(orderItemListLocator);
    }

    public String getItemName(WebElement item) {
        return item.findElement(orderItemName).getText();
    }

    public String getOrderNumber() {
        return orderNumber.getText().substring(1, 27).trim();
    }

    public void verifyProductInOrder(String expectedItem) {
        String actualItem = getOrderItemList().stream().filter(item ->
                getItemName(item).equalsIgnoreCase(expectedItem)).findFirst().orElse(null).getText();
        Assert.assertNotNull(actualItem);
    }
}
