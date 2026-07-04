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
        return driver.findElement(pageTitle).getText();
    }

    public String getItemNameText() {
        return driver.findElement(itemName).getText();
    }

    public String getItemPriceText() {
        return driver.findElement(itemPrice).getText();
    }

    public String getPaymentInfoValueText() {
        return driver.findElement(paymentInfoValue).getText();
    }

    public String getShippingInfoValueText() {
        return driver.findElement(shippingInfoValue).getText();
    }

    public String getItemTotalText() {
        return driver.findElement(itemTotalLabel).getText();
    }

    public String getTaxText() {
        return driver.findElement(taxLabel).getText();
    }

    public String getTotalText() {
        return driver.findElement(totalLabel).getText();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }
}