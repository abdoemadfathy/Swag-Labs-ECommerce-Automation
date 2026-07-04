package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage
{
    private final WebDriver driver;

    // Locators
    private final By userNameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By productsHeaderTitle = By.className("title");
    private final By errorMessageContainer = By.xpath("//h3[@data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions (Methods)

    // كتابة اسم المستخدم
    public void enterUserName(String userName) {
        driver.findElement(userNameField).sendKeys(userName);
    }

    // كتابة كلمة المرور
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // الضغط على زر الدخول
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    /**
     * دالة تسجيل الدخول الموحدة
     */
    public void Login(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        clickLoginButton();
    }

    // جلب نص عنوان صفحة المنتجات للتأكد من نجاح الدخول
    public String getProductsText() {
        return driver.findElement(productsHeaderTitle).getText();
    }

    /**
     * دالة موحدة لجلب أي رسالة خطأ تظهر في الصفحة
     */
    public String getErrorMessageText() {
        return driver.findElement(errorMessageContainer).getText();
    }
}