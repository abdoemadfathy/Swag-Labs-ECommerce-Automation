package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductListPage {
    private final WebDriver driver;

    private final By productTitleLocator = By.className("inventory_item_name");
    private final By addToCartButtonLocator = By.xpath("//button[contains(@data-test, 'add-to-cart')]");
    private final By cartBadgeLocator = By.xpath("//span[@data-test='shopping-cart-badge']");
    private final By removeButtonLocator = By.xpath("//button[@class=\"btn btn_secondary btn_small btn_inventory \"]");
    private final By dropdownLocator = By.className("product_sort_container");
    private final By firstProductLocator_A_To_Z = By.xpath("//div[@class=\"inventory_item_name \"]");
    private final By CartIconLocator = By.xpath("//a[@class=\"shopping_cart_link\"]");

    // Constructor
    public ProductListPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods

    // الضغط على اسم المنتج لفتح صفحته
    public void clickOnProductTitle(int index) {
        driver.findElements(productTitleLocator).get(index).click();
    }

    // الضغط على زرار Add to Cart بناءً على الـ index
    public void clickAddToCartButton(int index) {
        driver.findElements(addToCartButtonLocator).get(index).click();
    }

    // الضغط على زرار Remove بناءً على الـ index
    public void clickRemoveButton(int index) {
        driver.findElements(removeButtonLocator).get(index).click();
    }

    // جلب نص زرار الـ Remove لمنتج معين (تم تحديثها لتأخذ index عشان الدقة)
    public String getRemoveButtonText(int index) {
        return driver.findElements(removeButtonLocator).get(index).getText();
    }

    // جلب الرقم (العدد) الظاهر على أيقونة السلة
    public String getCartBadgeText() {
        return driver.findElement(cartBadgeLocator).getText();
    }

    // داله  تختار الترتيب بناءً على النص اللي هتبعته ليها
    public void selectSortOption(int index)
    {
        WebElement dropDownOptions = driver.findElement(dropdownLocator);
        Select select = new Select(dropDownOptions);
        select.selectByIndex(index);
    }

    // استدعاء اول اسم منتج بعد الترتيب من Z الى A
    public String getFirstProductName()
    {
       return driver.findElement(firstProductLocator_A_To_Z).getText();
    }

    // الضغط ع ايقونة السلة
    public void clickOnCartIcon()
    {
        driver.findElement(CartIconLocator).click();
    }

}