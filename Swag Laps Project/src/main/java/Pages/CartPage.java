package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private final WebDriver driver;

    // 1. Locators (Enterprise Style using data-test)
    private final By cartPageTitle = By.className("title"); // للتأكد أننا في صفحة Your Cart
    private final By cartItemName = By.xpath("//div[@data-test='inventory-item-name']");
    private final By cartItemPrice = By.xpath("//div[@data-test='inventory-item-price']");
    private final By cartItemQuantity = By.className("cart_quantity");
    private final By removeButton = By.xpath("//button[contains(@data-test, 'remove')]");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // 2. Methods (Actions)

    // جلب نص عنوان الصفحة الحالي ( Your Cart)
    public String getPageTitleText() {
        return driver.findElement(cartPageTitle).getText();
    }

    // جلب اسم المنتج الموجود داخل السلة بناءً على الـ index
    public String getCartItemNameText(int index) {
        return driver.findElements(cartItemName).get(index).getText();
    }

    // جلب سعر المنتج داخل السلة بناءً على الـ index
    public String getCartItemPriceText(int index) {
        return driver.findElements(cartItemPrice).get(index).getText();
    }

    // جلب كمية المنتج داخل السلة (المتوقع "1")
    public String getCartItemQuantityText(int index) {
        return driver.findElements(cartItemQuantity).get(index).getText();
    }

    // الضغط على زر Remove لإزالة منتج معين من داخل السلة
    public void clickRemoveButton(int index) {
        driver.findElements(removeButton).get(index).click();
    }

    // الضغط على زر Continue Shopping للعودة لصفحة المنتجات
    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    // الضغط على زر Checkout لبدء إتمام الشراء
    public void clickCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }

    // دالة ترجع عدد المنتجات المعروضة حالياً داخل السلة
    public int getCartItemsCount() {
        // بنجيب حجم القائمة، لو السلة فاضية المفروض يرجع 0
        return driver.findElements(cartItemName).size();
    }
}