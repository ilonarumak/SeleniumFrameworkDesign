package org.ilonarumak.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrdersPage extends AbstractPage {
    private final WebDriver driver;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final By ordersListLocator = By.cssSelector("table tbody tr");
    private final By orderNumberLocator = By.cssSelector("th");
    private final By productNameLocator = By.cssSelector("td:nth-child(3)");

    public List<WebElement> getOrderList() {
        return driver.findElements(ordersListLocator);
    }

    public String getOrderNumber(WebElement element) {
        return element.findElement(orderNumberLocator).getText();
    }

    public String getOrderName(WebElement element) {
        return element.findElement(productNameLocator).getText();
    }

    public WebElement getOrder(String expectedOrderNumber) {
        WebElement order = getOrderList().stream().filter(o ->
                getOrderNumber(o).equalsIgnoreCase(expectedOrderNumber.trim())).findFirst().orElse(null);
        Assert.assertNotNull(order);
        return order;
    }
}
