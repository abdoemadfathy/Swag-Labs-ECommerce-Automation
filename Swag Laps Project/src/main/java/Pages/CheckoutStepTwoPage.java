package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage {

    private final WebDriver driver;

    // 1. Locators
    private final By pageTitle = By.className("title"); // Checkout: Overview
    private final By itemName = By.xpath("//div[@data-test='inventory-item-name']");
    private final By itemPrice = By.xpath("//div[@data-test='inventory-item-price']");

    // محددات معلومات الفاتورة والأسعار المعروضة في الصورة الثالثة
    private final By paymentInfoValue = By.xpath("//div[@data-test='payment-info-value']");
    private final By shippingInfoValue = By.xpath("//div[@data-test='shipping-info-value']");
    private final By itemTotalLabel = By.className("summary_subtotal_label"); // Item total: $29.99
    private final By taxLabel = By.className("summary_tax_label"); // Tax: $2.40
    private final By totalLabel = By.className("summary_total_label"); // Total: $32.39
    private final By cancelButton = By.id("cancel");
    private final By finishButton = By.id("finish");

    // Constructor
    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
    }

    // 2. Methods
    public String getPageTitleText() {
        return Wait.text(driver, pageTitle);
    }

    public String getItemNameText() {
        return Wait.text(driver, itemName);
    }

    public String getItemPriceText() {
        return Wait.text(driver, itemPrice);
    }

    public String getPaymentInfoValueText() {
        return Wait.text(driver, paymentInfoValue);
    }

    public String getShippingInfoValueText() {
        return Wait.text(driver, shippingInfoValue);
    }

    public String getItemTotalText() {
        return Wait.text(driver, itemTotalLabel);
    }

    public String getTaxText() {
        return Wait.text(driver, taxLabel);
    }

    public String getTotalText() {
        return Wait.text(driver, totalLabel);
    }

    public void clickCancel() {
        Wait.click(driver, cancelButton);
    }

    public void clickFinish() {
        Wait.clickUntil(driver, finishButton, 0, d -> d.getCurrentUrl().contains("/checkout-complete.html") && d.getPageSource().contains("Thank you for your order!"));
    }
}