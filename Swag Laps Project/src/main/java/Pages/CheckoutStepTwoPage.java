package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePage {

    // 1. Locators
    private final By pageTitle = By.className("title"); // Checkout: Overview
    private final By itemName = By.xpath("//div[@data-test='inventory-item-name']");
    private final By itemPrice = By.xpath("//div[@data-test='inventory-item-price']");

    // محددات معلومات الفاتورة والأسعار المعروضة في صفحة Overview
    private final By paymentInfoValue = By.xpath("//div[@data-test='payment-info-value']");
    private final By shippingInfoValue = By.xpath("//div[@data-test='shipping-info-value']");
    private final By itemTotalLabel = By.className("summary_subtotal_label"); // Item total: $29.99
    private final By taxLabel = By.className("summary_tax_label"); // Tax: $2.40
    private final By totalLabel = By.className("summary_total_label"); // Total: $32.39
    private final By cancelButton = By.id("cancel");
    private final By finishButton = By.id("finish");

    // Constructor
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    // 2. Methods

    // انتظار الوصول لصفحة Checkout Step Two (الـ id "cancel" موجود كمان في الخطوة الأولى)
    public void waitForPage() {
        waitForUrlContains("/checkout-step-two.html");
    }

    public String getPageTitleText() {
        return getText(pageTitle);
    }

    public String getItemNameText() {
        return getText(itemName);
    }

    public String getItemPriceText() {
        return getText(itemPrice);
    }

    public String getPaymentInfoValueText() {
        return getText(paymentInfoValue);
    }

    public String getShippingInfoValueText() {
        return getText(shippingInfoValue);
    }

    public String getItemTotalText() {
        return getText(itemTotalLabel);
    }

    public String getTaxText() {
        return getText(taxLabel);
    }

    public String getTotalText() {
        return getText(totalLabel);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public void clickFinish() {
        click(finishButton);
    }
}
