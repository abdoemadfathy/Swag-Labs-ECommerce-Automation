package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Small helpers that retry when the page re-renders between finding an element and using it
 * (stale element), which is common on single-page apps and faster on CI.
 */
final class Wait {
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private Wait() {
    }

    static String text(WebDriver driver, By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> d.findElement(locator).getText());
    }

    static void click(WebDriver driver, By locator) {
        new WebDriverWait(driver, TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    WebElement element = d.findElement(locator);
                    element.click();
                    return true;
                });
    }

    /**
     * Clicks the element at {@code index} and keeps retrying (native click, then script click) until
     * {@code effect} is true. A click that lands while the single-page app is still re-rendering can be
     * dropped, so the action is confirmed by its visible result instead of assumed.
     */
    static void clickUntil(WebDriver driver, By locator, int index, java.util.function.Predicate<WebDriver> effect) {
        int[] attempt = {0};
        // The implicit wait would make every "is it gone yet?" lookup block for its full length, so poll without it.
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(IndexOutOfBoundsException.class)
                .until(d -> {
                    if (attempt[0] > 0 && effect.test(d)) {
                        return true;
                    }
                    WebElement element = d.findElements(locator).get(index);
                    if (attempt[0]++ % 2 == 0) {
                        element.click();
                    } else {
                        ((org.openqa.selenium.JavascriptExecutor) d).executeScript("arguments[0].click();", element);
                    }
                    return effect.test(d);
                });
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
}
