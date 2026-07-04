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

public class CheckoutStepTwo_TestCases extends BaseClass {

    private LoginPage loginPage;
    private ProductListPage productListPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    @BeforeMethod
    public void setUpCheckoutStepTwoTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);

        // التوجه المباشر لصفحة الخطوة الثانية ببيانات صحيحة
        loginPage.Login("standard_user", "secret_sauce");
        productListPage.clickAddToCartButton(0);
        Thread.sleep(2000);
        productListPage.clickOnCartIcon();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.fillCheckoutInformation("abdo", "emad", "12345");
        checkoutStepOnePage.clickContinue();
    }

    @Test(priority = 1, description = "Verify that the user is on Checkout Step Two (Overview) page")
    public void verifyNavigatingToCheckoutStepTwo() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertEquals(checkoutStepTwoPage.getPageTitleText(), "Checkout: Overview", "Page title text mismatch!");
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-two.html"), "URL mismatch!");
    }

    @Test(priority = 2, description = "Verify that the displayed items details match the order info")
    public void verifyProductDetailsOnOverviewPage() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertEquals(checkoutStepTwoPage.getItemNameText(), "Sauce Labs Backpack", "Product name mismatch!");
        Assert.assertEquals(checkoutStepTwoPage.getItemPriceText(), "$29.99", "Product individual price mismatch!");
    }

    @Test(priority = 3, description = "Verify summary info details (Payment, Shipping, Item Total, Tax, and Final Total)")
    public void verifyOrderSummaryCalculations() throws InterruptedException {
        Thread.sleep(2000);
        // المطابقة بناءً على البيانات الظاهرة في الصورة الثالثة المرفقة بدقة
        Assert.assertTrue(checkoutStepTwoPage.getPaymentInfoValueText().contains("SauceCard #31337"), "Payment info mismatch!");
        Assert.assertEquals(checkoutStepTwoPage.getShippingInfoValueText(), "Free Pony Express Delivery!", "Shipping info mismatch!");

        Assert.assertTrue(checkoutStepTwoPage.getItemTotalText().contains("$29.99"), "Item total price calculated incorrectly!");
        Assert.assertTrue(checkoutStepTwoPage.getTaxText().contains("$2.40"), "Tax value calculated incorrectly!");
        Assert.assertTrue(checkoutStepTwoPage.getTotalText().contains("$32.39"), "Final total price calculated incorrectly!");
    }

    @Test(priority = 4, description = "Verify that Cancel button on Step Two returns the user to the Main Product Page")
    public void verifyCancelButtonNavigatesToInventory() throws InterruptedException {
        Thread.sleep(2000);
        checkoutStepTwoPage.clickCancel();
        Thread.sleep(2000);
        // الضغط على إلغاء في الخطوة الثانية يرجع لصفحة المنتجات الرئيسية (inventory) حسب تصميم الموقع الرسمي لشاشات سواج لابس.
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "Cancel did not redirect to product inventory!");
    }

    @Test(priority = 5, description = "Verify that clicking Finish button successfully places the order and navigates to complete page")
    public void verifyFinishButtonNavigatesToCheckoutCompletePage() {
        // 1. الضغط على زر Finish لإتمام عملية الشراء
        checkoutStepTwoPage.clickFinish();

        // 2. الـ Assertions: التأكد من الانتقال لصفحة النجاح وظهور رسالة التأكيد
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-complete.html"), "URL does not contain checkout-complete.html");

        // التأكد من ظهور عنوان الشكر الأخري "Thank you for your order!" المشهور في موقع سواج لابس
        boolean isHeaderDisplayed = driver.getPageSource().contains("Thank you for your order!");
        Assert.assertTrue(isHeaderDisplayed, "Success header message 'Thank you for your order!' is missing!");
    }
}