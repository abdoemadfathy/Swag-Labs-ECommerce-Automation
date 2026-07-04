package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage {

    private final WebDriver driver;

    // 1. Locators (Enterprise Style using data-test or standard IDs)
    private final By pageTitle = By.className("title"); // للتأكد من العنوان Checkout: Your Information
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");

    // محددات رسائل الخطأ (Negative Scenarios)
    private final By errorMessageContainer = By.xpath("//div[contains(@class, 'error-message-container')]");
    private final By errorText = By.xpath("//h3[@data-test='error']");

    // Constructor
    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
    }

    // 2. Methods (Actions)

    public String getPageTitleText() {
        return driver.findElement(pageTitle).getText();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        driver.findElement(postalCodeField).clear();
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    // دالة مجمعة لتسهيل ملء البيانات دفعة واحدة
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    // جلب نص رسالة الخطأ الظاهرة
    public String getErrorMessageText() {
        return driver.findElement(errorText).getText();
    }
}