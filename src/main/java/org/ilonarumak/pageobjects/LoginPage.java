package org.ilonarumak.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userEmail")
    WebElement email;

    @FindBy(id = "userPassword")
    WebElement password;

    @FindBy(className = "login-btn")
    WebElement loginButton;

    @FindBy(css = ".login-wrapper-footer-text")
    WebElement registerButton;

    public void setEmail(String emailValue) {
        email.sendKeys(emailValue);
    }

    public void setPassword(String passwordValue) {
        password.sendKeys(passwordValue);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public RegistrationPage clickRegisterButton() {
        registerButton.click();
        return new RegistrationPage(driver);
    }

    public ProductCatalogPage login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        return new ProductCatalogPage(driver);
    }
}
