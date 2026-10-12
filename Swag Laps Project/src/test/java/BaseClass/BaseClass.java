package BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for all test classes.
 *
 * Runtime configuration (all optional, passed as -D system properties):
 *   -Dbrowser=chrome|firefox|edge   (default: chrome)
 *   -Dheadless=true|false           (default: false)
 *   -DbaseUrl=https://...           (default: https://www.saucedemo.com)
 */
public class BaseClass
{
    private static final String DEFAULT_BASE_URL = "https://www.saucedemo.com";

    public static WebDriver driver;

    @BeforeMethod
    public void setUp()
    {
        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        driver = createDriver(browser, headless);
        driver.get(System.getProperty("baseUrl", DEFAULT_BASE_URL));
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    // alwaysRun = true: المتصفح لازم يتقفل حتى لو الـ setUp بتاع الـ test class فشل
    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static WebDriver createDriver(String browser, boolean headless)
    {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new ChromeDriver(options);
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless", "--width=1920", "--height=1080");
                }
                return new FirefoxDriver(options);
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new EdgeDriver(options);
            }
            default:
                throw new IllegalArgumentException(
                        "Unsupported browser '" + browser + "'. Use chrome, firefox or edge.");
        }
    }
}
