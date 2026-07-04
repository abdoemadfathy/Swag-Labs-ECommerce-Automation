package TestCases;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.ProductListPage;
import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPage_TestCases extends BaseClass {

    private LoginPage loginPage;
    private ProductListPage productListPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpCartPageTest() {
        loginPage = new LoginPage(driver);
        productListPage = new ProductListPage(driver);
        cartPage = new CartPage(driver);

        // الخطوات الثابتة للوصول لصفحة السلة

        // 1. تسجيل الدخول
        loginPage.Login("standard_user", "secret_sauce");

        // 2. إضافة المنتج الأول (Index 0) كمثال للاختبار عليه
        productListPage.clickAddToCartButton(0);

        // 3. الضغط على أيقونة عربة التسوق لفتح صفحة السلة
        productListPage.clickOnCartIcon();
    }

    @Test(priority = 1, description = "Verify that the user is successfully navigated to the Cart page")
    public void verifyNavigatingToCartPage() {
        // الـ Assertion: التحقق من عنوان الصفحة والـ URL
        Assert.assertEquals(cartPage.getPageTitleText(), "Your Cart", "Page title text mismatch!");
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart.html"), "URL does not contain cart.html");
    }

    @Test(priority = 2, description = "Verify that the added product details (Name, Price, Qty) are correct inside the cart")
    public void verifyAddedProductDetailsInCart() throws InterruptedException {
        // الـ Assertions: التحقق من صحة البيانات المعروضة داخل السلة للمنتج الأول
        String actualName = cartPage.getCartItemNameText(0);
        String actualPrice = cartPage.getCartItemPriceText(0);
        String actualQty = cartPage.getCartItemQuantityText(0);

        Thread.sleep(2000);
        Assert.assertEquals(actualName, "Sauce Labs Backpack", "Product name in cart is incorrect!");
        Assert.assertEquals(actualPrice, "$29.99", "Product price in cart is incorrect!");
        Assert.assertEquals(actualQty, "1", "Product quantity in cart is incorrect!");
    }

    @Test(priority = 3, description = "Verify that removing the last product clears the cart completely (Empty State)")
    public void verifyCartBecomesEmptyAfterRemovingProduct() {
        // 1. الضغط على زر Remove لأول منتج موجود في السلة
        cartPage.clickRemoveButton(0);

        // 2. جلب عدد المنتجات المتبقية في السلة باستخدام الدالة الجديدة
        int actualItemsCount = cartPage.getCartItemsCount();

        // 3. الـ Assertion الاحترافي: المتوقع إن العدد يكون 0 تماماً كصورة الشاشة الحالية
        Assert.assertEquals(actualItemsCount, 0, "The cart is not empty after removing the product!");

        // 4. تأكيد إضافي (اختياري): التأكد أن النص الخاص بـ "Sauce Labs Backpack" مابقاش موجود في الصفحة
        Assert.assertFalse(driver.getPageSource().contains("Sauce Labs Backpack"), "Product text is still visible in the DOM!");
    }

    @Test(priority = 4, description = "Verify that removing a product from inside the cart clears the cart items")
    public void verifyRemoveProductFromCart() {
        // الضغط على Remove من داخل صفحة السلة
        cartPage.clickRemoveButton(0);

        // الـ Assertion: نتحقق أن النص الخاص بالمنتج لم يعد موجوداً في الصفحة بالكامل
        Assert.assertFalse(driver.getPageSource().contains("Sauce Labs Backpack"), "Product was not removed from cart!");
    }

    @Test(priority = 5, description = "Verify that 'Continue Shopping' button takes the user back to the product list")
    public void verifyContinueShoppingNavigation() {
        cartPage.clickContinueShopping();

        // الـ Assertion: التأكد من العودة لصفحة المنتجات الرئيسية
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "Continue Shopping didn't redirect correctly!");
    }

    @Test(priority = 6, description = "Verify that 'Checkout' button navigates the user to the checkout information page")
    public void verifyCheckoutButtonNavigation() {
        cartPage.clickCheckoutButton();

        // الـ Assertion: التأكد من الانتقال لخطوة الـ Checkout الأولى (بيانات الشحن)
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"), "Checkout button didn't redirect correctly!");
    }
}