package BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass
{
    public static WebDriver driver;
    @BeforeMethod
    public void setUp()
    {
        // On CI (GitHub Actions sets CI=true) run headless with a fixed window; locally keep the visible, maximized browser.
        boolean ci = System.getenv("CI") != null;
        ChromeOptions options = new ChromeOptions();
        if (ci) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        // Let element lookups wait for the page to render instead of failing on timing.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
        if (!ci) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null) {
            driver.quit();
        }
    }
}