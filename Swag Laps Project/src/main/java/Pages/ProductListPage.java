package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductListPage extends BasePage {

    private final By productTitleLocator = By.className("inventory_item_name");
    private final By addToCartButtonLocator = By.xpath("//button[contains(@data-test, 'add-to-cart')]");
    private final By cartBadgeLocator = By.xpath("//span[@data-test='shopping-cart-badge']");
    private final By removeButtonLocator = By.xpath("//button[contains(@data-test, 'remove')]");
    private final By dropdownLocator = By.className("product_sort_container");
    private final By cartIconLocator = By.className("shopping_cart_link");

    // Constructor
    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    // Methods

    // انتظار الوصول لصفحة المنتجات الرئيسية
    public void waitForPage() {
        waitForUrlContains("/inventory.html");
    }

    // الضغط على اسم المنتج لفتح صفحته
    public void clickOnProductTitle(int index) {
        clickElementAt(productTitleLocator, index);
    }

    // الضغط على زرار Add to Cart بناءً على الـ index
    public void clickAddToCartButton(int index) {
        clickElementAt(addToCartButtonLocator, index);
    }

    // الضغط على زرار Remove بناءً على الـ index
    public void clickRemoveButton(int index) {
        clickElementAt(removeButtonLocator, index);
    }

    // جلب نص زرار الـ Remove لمنتج معين (تم تحديثها لتأخذ index عشان الدقة)
    public String getRemoveButtonText(int index) {
        return getTextAt(removeButtonLocator, index);
    }

    // جلب الرقم (العدد) الظاهر على أيقونة السلة
    public String getCartBadgeText() {
        return getText(cartBadgeLocator);
    }

    // داله  تختار الترتيب بناءً على الـ index اللي هتبعته ليها
    public void selectSortOption(int index)
    {
        WebElement dropDownOptions = waitForVisible(dropdownLocator);
        Select select = new Select(dropDownOptions);
        select.selectByIndex(index);
    }

    // استدعاء اول اسم منتج ظاهر في القائمة (بعد أي ترتيب)
    public String getFirstProductName()
    {
       return getTextAt(productTitleLocator, 0);
    }

    // الضغط ع ايقونة السلة
    public void clickOnCartIcon()
    {
        click(cartIconLocator);
    }

}
