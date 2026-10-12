package Pages;

import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    // Constructor
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    // انتظار الوصول لصفحة تأكيد الطلب بعد الضغط على Finish
    public void waitForPage() {
        waitForUrlContains("/checkout-complete.html");
    }
}
