package org.ilonarumak.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CheckoutPage extends AbstractPage {
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private final By shippingCountryOptionsLocator = By.cssSelector("button.list-group-item");

    @FindBy(className = "payment__type")
    List<WebElement> paymentMethodsList;

    @FindBy(css = ".payment__cc .row:first-child input[type='text']")
    WebElement creditCardNumberField;

    @FindBy(css = ".payment__cc select:first-of-type")
    WebElement expiryMonthDropdown;

    @FindBy(css = ".payment__cc select:last-of-type")
    WebElement expiryYearDropdown;

    @FindBy(css = ".payment__cc .row:nth-child(2) input[type='text']")
    WebElement cvvCodeField;

    @FindBy(css = ".payment__cc .row:nth-child(3) input[type='text']")
    WebElement nameOnCardField;

    @FindBy(css = ".payment__cc .row:nth-child(4) input[type='text']")
    WebElement couponField;

    @FindBy(css = ".payment__cc .row:nth-child(4) button")
    WebElement applyCouponButton;

    @FindBy(xpath = "//div[@class='user__address']/preceding-sibling::input")
    WebElement shippingInfoNameField;

    @FindBy(xpath = "//input[@placeholder='Select Country']")
    WebElement shippingCountryField;

    @FindBy(css = "a.btnn")
    WebElement placeOrderButton;

    public void selectPaymentMethod(String paymentMethod) {
        WebElement paymentMethodOption = paymentMethodsList.stream().filter(item ->
                item.getText().equalsIgnoreCase(paymentMethod)).findFirst().orElse(null);
        Assert.assertNotNull(paymentMethodOption);
        paymentMethodOption.click();
    }

    public void setCreditCardNumber(String creditCardNumber) {
        creditCardNumberField.clear();
        creditCardNumberField.sendKeys(creditCardNumber);
    }

    public void setExpiryDate(String expiryMonth, String expiryYear) {
        Select monthDropdown = new Select(expiryMonthDropdown);
        monthDropdown.selectByVisibleText(expiryMonth);
        Select yearDropdown = new Select(expiryYearDropdown);
        yearDropdown.selectByVisibleText(expiryYear);
    }

    public void setCvvCode(String cvvCode) {
        cvvCodeField.sendKeys(cvvCode);
    }

    public void setNameOnCard(String nameOnCard) {
        nameOnCardField.sendKeys(nameOnCard);
    }

    public void applyCoupon(String coupon) {
        couponField.sendKeys(coupon);
        applyCouponButton.click();
    }

    public void setShippingInfoName(String name) {
        shippingInfoNameField.clear();
        shippingInfoNameField.sendKeys(name);
    }

    public void setShippingCountry(String country) {
        shippingCountryField.sendKeys(country.substring(0, 2));
        List<WebElement> options = driver.findElements(shippingCountryOptionsLocator);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(country)) {
                option.click();
                break;
            }
        }
    }

    public OrderConfirmationPage clickPlaceOrderButton() {
        placeOrderButton.click();
        return new OrderConfirmationPage(driver);
    }
}
