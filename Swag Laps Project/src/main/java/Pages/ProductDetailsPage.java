package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
        return Wait.text(driver, productNameTitle);
    }

    // جلب وصف المنتج
    public String getProductDescription() {
        return Wait.text(driver, productDescriptionText);
    }

    // جلب سعر المنتج
    public String getProductPrice() {
        return Wait.text(driver, productPriceLabel);
    }

    // الضغط على Add to cart
    public void clickAddToCartButton() {
        Wait.click(driver, addToCartButton);
    }

    // الضغط على Remove
    public void clickRemoveButton() {
        Wait.click(driver, removeButton);
    }

    // جلب نص زرار الـ Remove للتأكد من التحول
    public String getRemoveButtonText() {
        // The button is re-rendered after "Add to cart", so retry if the element goes stale.
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> d.findElement(removeButton).getText());
    }

    // الضغط على زرار العودة للخلف Back to products
    public void clickBackToProducts() {
        Wait.click(driver, backToProductsButton);
    }

    // جلب رقم السلة (العداد)
    public String getCartBadgeText() {
        return Wait.text(driver, cartBadgeCounter);
    }
}