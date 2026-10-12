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
}
