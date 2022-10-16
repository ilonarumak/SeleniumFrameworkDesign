package org.ilonarumak.tests;

import org.ilonarumak.pageobjects.RegistrationPage;
import org.ilonarumak.tests.testComponents.BaseTest;
import org.junit.Assert;
import org.testng.annotations.Test;

public class NewUserRegistrationTest extends BaseTest {

    @Test
    public void registerSuccessfully() {
        RegistrationPage registrationPage = loginPage.clickRegisterButton();
        registrationPage.setFirstName(userDataProperties.getProperty("firstName"));
        registrationPage.setLastName(userDataProperties.getProperty("lastName"));
        registrationPage.setEmail(userDataProperties.getProperty("email"));
        registrationPage.setPhoneNumber(userDataProperties.getProperty("phone"));
        registrationPage.setOccupation(userDataProperties.getProperty("occupation"));
        registrationPage.setGender(userDataProperties.getProperty("gender"));
        registrationPage.setPassword(userDataProperties.getProperty("password"));
        registrationPage.setConfirmationPassword(userDataProperties.getProperty("password"));
        registrationPage.setAgeConfirmation();
        registrationPage.clickRegisterButton();
        String successfulMessage = registrationPage.getSuccessfulMessage();
        Assert.assertTrue(successfulMessage.equalsIgnoreCase("Account Created Successfully"));
    }
}