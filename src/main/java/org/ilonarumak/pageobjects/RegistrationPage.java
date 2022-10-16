package org.ilonarumak.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage {
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement email;

    @FindBy(id = "userMobile")
    WebElement phone;

    @FindBy(xpath = "//select[@formcontrolname='occupation']")
    WebElement occupationDropdown;

    @FindBy(xpath = "//input[@value='Male']")
    WebElement maleGender;

    @FindBy(xpath = "//input[@value='Female']")
    WebElement femaleGender;

    @FindBy(id = "userPassword")
    WebElement password;

    @FindBy(id = "confirmPassword")
    WebElement passwordConfirmation;

    @FindBy(xpath = "//input[@type='checkbox']")
    WebElement ageConfirmationCheckbox;

    @FindBy(xpath = "//input[@value='Register']")
    WebElement registerButton;

    @FindBy(css = ".headcolor")
    WebElement successfulMessage;

    public void setFirstName(String firstNameValue) {
        firstName.sendKeys(firstNameValue);
    }

    public void setLastName(String lastNameValue) {
        lastName.sendKeys(lastNameValue);
    }

    public void setEmail(String emailValue) {
        email.sendKeys(emailValue);
    }

    public void setPhoneNumber(String phoneNumber) {
        phone.sendKeys(phoneNumber);
    }

    public void setOccupation(String occupation) {
        Select dropdown = new Select(occupationDropdown);
        dropdown.selectByVisibleText(occupation);
    }

    public void setGender(String genderName) {
        if (genderName.equalsIgnoreCase("male")) {
            maleGender.click();
        } else if (genderName.equalsIgnoreCase("female")) {
            femaleGender.click();
        }
    }

    public void setPassword(String passwordValue) {
        password.sendKeys(passwordValue);
    }

    public void setConfirmationPassword(String password) {
        passwordConfirmation.sendKeys(password);
    }

    public void setAgeConfirmation() {
        ageConfirmationCheckbox.click();
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public String getSuccessfulMessage() {
        return successfulMessage.getText();
    }
}
