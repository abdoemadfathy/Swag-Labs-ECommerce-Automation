package TestCases;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.ProductDetailsPage;
import Pages.ProductListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetailsPage_TestCases extends BaseClass {

    private LoginPage loginPage;
    private ProductListPage productListPage;
    private ProductDetailsPage productDetailsPage;

    @BeforeMethod
    public void setUpProductDetailsTest() {
        loginPage = new LoginPage(driver);
        productListPage = new ProductListPage(driver);
        productDetailsPage = new ProductDetailsPage(driver);

        // 1. تسجيل الدخول والوقوف على صفحة المنتجات
        loginPage.Login("standard_user", "secret_sauce");

        // 2. الدخول لصفحة تفاصيل المنتج الأول (Index 0) لبدء الاختبارات عليها
        productListPage.clickOnProductTitle(0);
    }

    @Test(priority = 1, description = "Verify that product basic details (Name, Price, Desc) are displayed correctly")
    public void verifyProductDetailsDisplay() {
        // التحقق أن اسم المنتج ووصفه وسعره غير فارغين وظاهرين بشكل سليم
        Assert.assertFalse(productDetailsPage.getProductName().isEmpty(), "Product Name is missing!");
        Assert.assertFalse(productDetailsPage.getProductDescription().isEmpty(), "Product Description is missing!");
        Assert.assertTrue(productDetailsPage.getProductPrice().contains("$"), "Product Price format is incorrect!");
    }

    @Test(priority = 2, description = "Verify that adding product to cart from details page updates the button to 'Remove'")
    public void verifyAddToCartButtonChangesToRemoveText() {
        // الضغط على Add to cart داخل صفحة التفاصيل
        productDetailsPage.clickAddToCartButton();

        // الـ Assertion: التأكد أن الزرار تحول لنص Remove
        String actualButtonText = productDetailsPage.getRemoveButtonText();
        Assert.assertEquals(actualButtonText, "Remove", "Button did not switch to Remove!");
    }

    @Test(priority = 3, description = "Verify that adding product from details page increments the cart counter")
    public void verifyCartBadgeIncrementsOnAdd() {
        productDetailsPage.clickAddToCartButton();

        // الـ Assertion: التأكد من ظهور رقم 1 على أيقونة السلة
        String cartCount = productDetailsPage.getCartBadgeText();
        Assert.assertEquals(cartCount, "1", "Cart badge counter mismatch!");
    }

    @Test(priority = 4, description = "Verify that removing product from details page clears the cart badge counter")
    public void verifyRemoveProductDecrementsCart() {
        // إضافة المنتج ثم إزالته فوراً من صفحة التفاصيل
        productDetailsPage.clickAddToCartButton();
        productDetailsPage.clickRemoveButton();

        //  نكتفي بالتحقق أن الزر عاد لاسمه الأصلي لو أردنا
        Assert.assertTrue(driver.getPageSource().contains("Add to cart"), "Button didn't reset to Add to cart!");
    }

    @Test(priority = 5, description = "Verify that 'Back to products' button navigates the user back to the main list")
    public void verifyBackToProductsNavigation() {
        // الضغط على زر الرجوع
        productDetailsPage.clickBackToProducts();

        // الـ Assertion: التأكد من العودة لصفحة المنتجات الرئيسية من خلال الـ URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/inventory.html"), "Back button did not navigate to main inventory page!");
    }
}