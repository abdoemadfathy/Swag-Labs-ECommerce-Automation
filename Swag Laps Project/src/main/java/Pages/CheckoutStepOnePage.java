package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage {

    // 1. Locators (Enterprise Style using data-test or standard IDs)
    private final By pageTitle = By.className("title"); // للتأكد من العنوان Checkout: Your Information
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");

    // محدد رسالة الخطأ (Negative Scenarios)
    private final By errorText = By.xpath("//h3[@data-test='error']");

    // Constructor
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    // 2. Methods (Actions)

    // انتظار الوصول لصفحة Checkout Step One
    public void waitForPage() {
        waitForUrlContains("/checkout-step-one.html");
    }

    public String getPageTitleText() {
        return getText(pageTitle);
    }

    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        type(postalCodeField, postalCode);
    }

    // دالة مجمعة لتسهيل ملء البيانات دفعة واحدة
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public void clickContinue() {
        click(continueButton);
    }

    // جلب نص رسالة الخطأ الظاهرة
    public String getErrorMessageText() {
        return getText(errorText);
    }
}
