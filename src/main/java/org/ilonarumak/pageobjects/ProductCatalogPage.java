package org.ilonarumak.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductCatalogPage extends AbstractPage {
    private WebDriver driver;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final By itemCardLocator = By.cssSelector("#products div.card");
    private final By itemNameLocator = By.xpath("//h5/b");
    private final By itemPriceLocator = By.cssSelector(".text-muted");
    private final By addToCartButtonLocator = By.cssSelector("button.w-10");


    public List<WebElement> getItemList() {
        return driver.findElements(itemCardLocator);
    }

    public WebElement getItem(String itemName) {
        List<WebElement> itemList = getItemList();
        return itemList.stream().filter(item ->
                getItemName(item).equalsIgnoreCase(itemName)).findFirst().orElse(null);
    }

    public String getItemName(WebElement element) {
        return element.findElement(itemNameLocator).getText();
    }

    public String getItemPrice(WebElement element) {
        return element.findElement(itemPriceLocator).getText().substring(1).trim();
    }

    public void clickAddToCartButton(WebElement element) {
        element.findElement(addToCartButtonLocator).click();
    }

    public void addItemToCart(String itemName) {
        WebElement item = getItem(itemName);
        Assert.assertNotNull(item);
        clickAddToCartButton(item);
    }
}
