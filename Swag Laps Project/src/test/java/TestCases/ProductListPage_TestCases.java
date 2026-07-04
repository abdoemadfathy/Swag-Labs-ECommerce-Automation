package TestCases;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.ProductDetailsPage;
import Pages.ProductListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductListPage_TestCases extends BaseClass
{
    private ProductListPage productListPage;
    private LoginPage loginPage;
    private ProductDetailsPage productDetailsPage;

    @BeforeMethod
    public void initializePages()
    {
        productListPage = new ProductListPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);
        loginPage = new LoginPage(driver);

        // تسجيل دخول تلقائي قبل كل تست
        loginPage.Login("standard_user", "secret_sauce");
    }

    @Test(priority = 1 , description = "Verify that the Add to Cart button successfully")
    public void verifyAddToCartButton()
    {
        // الضغط على إضافة المنتج الثالث (index 2)
        productListPage.clickAddToCartButton(2);

        // Assertion
        // التحقق أن زرار المنتج الذي تم ضغطه تحول نصّه إلى Remove
        String actualButtonText = productListPage.getRemoveButtonText(0);
        Assert.assertEquals(actualButtonText, "Remove", "The button text did not change to 'Remove'!");
    }

    @Test(priority = 2 ,description = "Verify that adding two products increments the cart badge count to 2")
    public void verifyCartBadgeIncrementsWhenMultipleProductsAdded()
    {
        // إضافة منتجين متتاليين
        productListPage.clickAddToCartButton(0);
        productListPage.clickAddToCartButton(0);

        // التحقق أن عداد السلة أصبح 2
        String actualCartCount = productListPage.getCartBadgeText();
        Assert.assertEquals(actualCartCount, "2", "The cart badge count is incorrect!");
    }

    @Test(priority = 3, description = "Verify click on product title lead to detail page")
    public void VerifyClickOnProductTitle() {
        productListPage.clickOnProductTitle(0);

        String expectedResult = "Sauce Labs Backpack";
        String actualResult = productDetailsPage.getProductName();

        // 4. المقارنة
        Assert.assertEquals(actualResult, expectedResult, "Product name on details page does not match!");
    }

    @Test(priority = 4 , description = "Verify that removing a product decrements the cart badge count correctly")
    public void verifyRemovingProductDecrementsCartBadge()
    {
        // 1. إضافة منتجين متتاليين (العداد هيبقى 2)
        productListPage.clickAddToCartButton(0);
        productListPage.clickAddToCartButton(0);

        // 2. الضغط على زرار الـ Remove لأول منتج تم إضافته
        productListPage.clickRemoveButton(0);

        // Assertion
        // 3. التحقق أن عداد السلة قلّ وأصبح "1"
        String actualCartCount = productListPage.getCartBadgeText();
        Assert.assertEquals(actualCartCount, "1", "The cart badge count did not decrement correctly!");
    }

    @Test(priority = 5 ,description = "Verify that sorting products works correctly using index")
    public void verifySortingProductsFromZToA() {
        // 1. استدعاء داله selectSortOption وتمرير index 1 (الخاص بترتيب Z to A)
        productListPage.selectSortOption(1);

        // Assertion
        // 2. جلب اسم أول منتج ظهر في الصفحة بعد الترتيب
        // (تأكد إن دالة productTitleLocator موجودة عندك وبتجيب أول منتج index 0)
        String actualFirstProduct = productListPage.getFirstProductName();

        // 3. الـ Assertion: المتوقع إن أول منتج يكون التيشرت الأحمر
        String expectedProduct = "Test.allTheThings() T-Shirt (Red)";

        Assert.assertEquals(actualFirstProduct, expectedProduct, "Sorting  failed!");
    }

}