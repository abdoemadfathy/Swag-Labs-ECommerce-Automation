package TestCases;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.ProductListPage;
import Pages.CartPage;
import Pages.CheckoutStepOnePage;
import Pages.CheckoutStepTwoPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutStepOne_TestCases extends BaseClass {

    private LoginPage loginPage;
    private ProductListPage productListPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    @BeforeMethod
    public void setUpCheckoutStepOneTest() {
        loginPage = new LoginPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);

        // خطوات الوصول لصفحة Checkout Step One
        loginPage.Login("standard_user", "secret_sauce");
        productListPage.clickAddToCartButton(0);
        productListPage.clickOnCartIcon();
        cartPage.waitForPage();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.waitForPage();
    }

    @Test(priority = 1, description = "Verify successful navigation to Checkout Step One page")
    public void verifyNavigatingToCheckoutStepOne() {
        Assert.assertEquals(checkoutStepOnePage.getPageTitleText(), "Checkout: Your Information", "Page title mismatch!");
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"), "URL mismatch!");
    }

    @Test(priority = 2, description = "Verify that Cancel button redirects user back to the Cart page")
    public void verifyCancelButtonNavigatesBackToCart() {
        checkoutStepOnePage.clickCancel();
        cartPage.waitForPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"), "Cancel button didn't redirect to cart page!");
    }

    @Test(priority = 3, description = "Verify error message when First Name is missing (Negative Scenario)")
    public void verifyErrorMessageWhenFirstNameIsEmpty() {
        checkoutStepOnePage.enterLastName("abdo");
        checkoutStepOnePage.enterPostalCode("67314");
        checkoutStepOnePage.clickContinue();

        Assert.assertEquals(checkoutStepOnePage.getErrorMessageText(), "Error: First Name is required", "Error message mismatch!");
    }

    @Test(priority = 4, description = "Verify error message when Last Name is missing (Negative Scenario)")
    public void verifyErrorMessageWhenLastNameIsEmpty() {
        checkoutStepOnePage.enterFirstName("abdo");
        checkoutStepOnePage.enterPostalCode("12345");
        checkoutStepOnePage.clickContinue();

        Assert.assertEquals(checkoutStepOnePage.getErrorMessageText(), "Error: Last Name is required", "Error message mismatch!");
    }

    @Test(priority = 5, description = "Verify error message when Postal Code is missing (Negative Scenario)")
    public void verifyErrorMessageWhenPostalCodeIsEmpty() {
        checkoutStepOnePage.enterFirstName("abdo");
        checkoutStepOnePage.enterLastName("emad");
        checkoutStepOnePage.clickContinue();

        Assert.assertEquals(checkoutStepOnePage.getErrorMessageText(), "Error: Postal Code is required", "Error message mismatch!");
    }

    @Test(priority = 6, description = "Verify successful form submission with valid data moving to Step Two")
    public void verifySuccessfulCheckoutStepOneSubmission() {
        checkoutStepOnePage.fillCheckoutInformation("abdo", "emad", "12345");
        checkoutStepOnePage.clickContinue();
        checkoutStepTwoPage.waitForPage();

        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-two.html"), "Valid data did not navigate to step two!");
    }
}
