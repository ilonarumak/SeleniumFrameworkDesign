package org.ilonarumak.tests;

import org.ilonarumak.tests.testComponents.BaseTest;
import org.ilonarumak.tests.testComponents.RetryFailedTest;
import org.junit.Assert;
import org.testng.annotations.Test;

public class ErrorValidationsTest extends BaseTest {

    @Test(retryAnalyzer = RetryFailedTest.class)
    public void validateLoginError() {
        loginPage.login("test@test.com", "test");
        String errorToastMessage = loginPage.getToastMessageText();
        Assert.assertEquals("Incorrect email or password.", errorToastMessage);
    }
}