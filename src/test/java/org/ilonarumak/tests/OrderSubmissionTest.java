package org.ilonarumak.tests;

import org.ilonarumak.pageobjects.*;
import org.ilonarumak.tests.testComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class OrderSubmissionTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void loginSuccessfully(HashMap<String, String> input) {
        loginPage.login(input.get("email"), input.get("password"));
    }

    @Test(dataProvider = "getData")
    public void addItemToCart(HashMap<Object, Object> input) {
        //select item on dashboard page
        ProductCatalogPage productCatalogPage = loginPage.login(input.get("email").toString(), input.get("password").toString());
        WebElement toastMessageContainer = productCatalogPage.getToastMessageElement();
        productCatalogPage.waitElementToDisappear(toastMessageContainer);
        productCatalogPage.addItemToCart(input.get("product").toString());
        String toastMessage = productCatalogPage.getToastMessageText();
        Assert.assertTrue(toastMessage.equalsIgnoreCase("Product Added To Cart"));
        String numberOfAddedProducts = productCatalogPage.getCartNumberOfProducts();
        Assert.assertEquals("1", numberOfAddedProducts);

        CartPage cartPage = productCatalogPage.clickCartButton();
        numberOfAddedProducts = cartPage.getCartNumberOfProducts();
        Assert.assertEquals("1", numberOfAddedProducts);
        WebElement addedItem = cartPage.getItem(input.get("product").toString());
        Assert.assertNotNull(addedItem);
        cartPage.clickCheckoutButton();
    }

    @Test(dataProvider = "getData")
    public void submitOrder(HashMap<Object, Object> input) {
        ProductCatalogPage productCatalogPage = loginPage.login(input.get("email").toString(), input.get("password").toString());
        productCatalogPage.addItemToCart(input.get("product").toString());
        WebElement toastMessageContainer = productCatalogPage.getToastMessageElement();
        productCatalogPage.waitElementToDisappear(toastMessageContainer);
        CartPage cartPage = productCatalogPage.clickCartButton();
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();

        checkoutPage.selectPaymentMethod(userDataProperties.getProperty("paymentMethod"));
        checkoutPage.setCreditCardNumber(userDataProperties.getProperty("creditCardNumber"));
        checkoutPage.setExpiryDate(userDataProperties.getProperty("expiryMonth"), userDataProperties.getProperty("expiryYear"));
        checkoutPage.setCvvCode(userDataProperties.getProperty("cvv"));
        checkoutPage.setNameOnCard(userDataProperties.getProperty("nameOnCard"));
        checkoutPage.setShippingInfoName(userDataProperties.getProperty("firstName") + " " + userDataProperties.getProperty("lastName"));
        checkoutPage.setShippingCountry(userDataProperties.getProperty("country"));
        OrderConfirmationPage orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        String confirmationMessage = orderConfirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("Thankyou for the order."));

        orderConfirmationPage.verifyProductInOrder(input.get("product").toString());
        String orderNumber = orderConfirmationPage.getOrderNumber();
        OrdersPage ordersPage = orderConfirmationPage.clickOrdersButton();

        WebElement order = ordersPage.getOrder(orderNumber);
        Assert.assertTrue(ordersPage.getOrderName(order).equalsIgnoreCase(input.get("product").toString()));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap("src/test/java/org/ilonarumak/data/submitOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}
