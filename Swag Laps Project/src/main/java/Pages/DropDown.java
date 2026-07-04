package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DropDown
{
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().window().maximize();

        // Implicit wait
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Explicit wait
        WebDriverWait wait = new WebDriverWait (driver , Duration.ofSeconds(5));

        driver.findElement(By.id("APjFqb")).sendKeys("gemini");

        By suggestions = By.xpath("//ul[@jsname='bw4e9b']//li[@role='presentation']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestions));
        List<WebElement> dropDownOptions = driver.findElements(suggestions);

        dropDownOptions.get(0).click();
        Thread.sleep(3000);
        driver.close();

    }
}