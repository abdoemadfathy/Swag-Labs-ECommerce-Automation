package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {

    private final WebDriver driver;

    // 1. Locators (Enterprise Style using data-test attributes)
    private final By productNameTitle = By.xpath("//div[contains(@data-test, 'inventory-item-name')]");
    private final By productDescriptionText = By.xpath("//div[contains(@data-test, 'inventory-item-desc')]");
    private final By productPriceLabel = By.xpath("//div[contains(@data-test, 'inventory-item-price')]");
    private final By addToCartButton = By.xpath("//button[contains(@data-test, 'add-to-cart')]");
    private final By removeButton = By.xpath("//button[contains(@data-test, 'remove')]");
    private final By backToProductsButton = By.id("back-to-products");
    private final By cartBadgeCounter = By.xpath("//span[@data-test='shopping-cart-badge']");

    // Constructor
    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // 2. Methods (Actions)

    // جلب اسم المنتج الحالي
    public String getProductName() {
        return driver.findElement(productNameTitle).getText();
    }

    // جلب وصف المنتج
    public String getProductDescription() {
        return driver.findElement(productDescriptionText).getText();
    }

    // جلب سعر المنتج
    public String getProductPrice() {
        return driver.findElement(productPriceLabel).getText();
    }

    // الضغط على Add to cart
    public void clickAddToCartButton() {
        driver.findElement(addToCartButton).click();
    }

    // الضغط على Remove
    public void clickRemoveButton() {
        driver.findElement(removeButton).click();
    }

    // جلب نص زرار الـ Remove للتأكد من التحول
    public String getRemoveButtonText() {
        return driver.findElement(removeButton).getText();
    }

    // الضغط على زرار العودة للخلف Back to products
    public void clickBackToProducts() {
        driver.findElement(backToProductsButton).click();
    }

    // جلب رقم السلة (العداد)
    public String getCartBadgeText() {
        return driver.findElement(cartBadgeCounter).getText();
    }
}