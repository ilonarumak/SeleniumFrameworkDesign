package org.ilonarumak.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public abstract class AbstractPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    private final By cartNumberOfProductsLocator = By.xpath("//button[@routerlink='/dashboard/cart']/label");
    private final By toastMessageLocator = By.cssSelector("#toast-container");

    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement cartButton;

    @FindBy(xpath = "//button[@routerlink='/dashboard/myorders']")
    WebElement ordersButton;

    @FindBy(css = "#toast-container")
    WebElement toastMessage;

    public CartPage clickCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
        return new CartPage(driver);
    }

    public OrdersPage clickOrdersButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        ordersButton.click();
        return new OrdersPage(driver);
    }

    public WebElement getToastMessageElement() {
        return toastMessage;
    }

    public String getToastMessageText() {
        wait.until(ExpectedConditions.textMatches(toastMessageLocator, Pattern.compile(".")));
        return driver.findElement(toastMessageLocator).getText().trim();
    }

    public void waitElementToDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public String getCartNumberOfProducts() {
        wait.until(ExpectedConditions.textMatches(cartNumberOfProductsLocator, Pattern.compile(".")));
        return driver.findElement(cartNumberOfProductsLocator).getText();
    }
}
