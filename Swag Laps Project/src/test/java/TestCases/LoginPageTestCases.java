package TestCases;

import BaseClass.BaseClass;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTestCases extends BaseClass
{
    private LoginPage loginPage;

    @BeforeMethod
    public void initializePage()
    {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void loginWithValidData()
    {
        loginPage.Login("standard_user", "secret_sauce");

        // Assertion: التحقق من نص عنوان صفحة المنتجات بعد الدخول
        String actualResult = loginPage.getProductsText();
        Assert.assertEquals(actualResult, "Products", "Failed to login: Products header text mismatch!");

        //  التحقق أن الـ URL تغير لصفحة المنتجات
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "URL does not contain inventory.html");
    }

    @Test(priority = 2, description = "Verify error message when logging in with invalid username")
    public void loginWithInvalidData()
    {
        loginPage.Login("invalid_user_xyz", "secret_sauce");

        String actualResult = loginPage.getErrorMessageText();
        String expectedResult = "Epic sadface: Username and password do not match any user in this service";

        Assert.assertEquals(actualResult, expectedResult, "Error message for invalid credentials mismatch!");
    }

    @Test(priority = 3, description = "Verify error message for a locked out user")
    public void loginWithLockedOutUser()
    {
        loginPage.Login("locked_out_user", "secret_sauce");

        String actualResult = loginPage.getErrorMessageText();
        String expectedResult = "Epic sadface: Sorry, this user has been locked out.";

        Assert.assertEquals(actualResult, expectedResult, "Error message for locked out user mismatch!");
    }

    @Test(priority = 4, description = "Verify error message when username field is empty")
    public void loginWithEmptyUserName()
    {
        loginPage.Login("", "secret_sauce");

        String actualResult = loginPage.getErrorMessageText();
        String expectedResult = "Epic sadface: Username is required";

        Assert.assertEquals(actualResult, expectedResult, "Error message for empty username mismatch!");
    }

    @Test(priority = 5, description = "Verify error message when password field is empty")
    public void loginWithEmptyPassword()
    {
        loginPage.Login("standard_user", "");

        String actualResult = loginPage.getErrorMessageText(); // دالة موحدة
        String expectedResult = "Epic sadface: Password is required";

        Assert.assertEquals(actualResult, expectedResult, "Error message for empty password mismatch!");
    }
}