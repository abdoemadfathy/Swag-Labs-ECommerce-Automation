package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {

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
        super(driver);
    }

    // 2. Methods (Actions)

    // انتظار الوصول لصفحة تفاصيل المنتج (نفس الـ data-test للاسم موجود في صفحة القائمة)
    public void waitForPage() {
        waitForUrlContains("/inventory-item.html");
    }

    // جلب اسم المنتج الحالي
    public String getProductName() {
        return getText(productNameTitle);
    }

    // جلب وصف المنتج
    public String getProductDescription() {
        return getText(productDescriptionText);
    }

    // جلب سعر المنتج
    public String getProductPrice() {
        return getText(productPriceLabel);
    }

    // الضغط على Add to cart
    public void clickAddToCartButton() {
        click(addToCartButton);
    }

    // الضغط على Remove
    public void clickRemoveButton() {
        click(removeButton);
    }

    // جلب نص زرار الـ Remove للتأكد من التحول
    public String getRemoveButtonText() {
        return getText(removeButton);
    }

    // هل زر Add to cart ظاهر (يعني المنتج مش في السلة)
    public boolean isAddToCartButtonDisplayed() {
        return waitForVisible(addToCartButton).isDisplayed();
    }

    // الضغط على زرار العودة للخلف Back to products
    public void clickBackToProducts() {
        click(backToProductsButton);
    }

    // جلب رقم السلة (العداد)
    public String getCartBadgeText() {
        return getText(cartBadgeCounter);
    }

    // هل عداد السلة اختفى (السلة فاضية)
    public boolean isCartBadgeAbsent() {
        return waitForInvisibility(cartBadgeCounter);
    }
}
