import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LogoutTest {
    private WebDriver driver;

    @BeforeScenario
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Cosmin\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Step("Navigate to <url>")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("Click on button <logout> ")
    public void clickOnButton(String button){
        WebElement clickButton = driver.findElement(By.name(button));
        clickButton.click();
    }
    @AfterScenario
    public void tearDown() {
        driver.quit();
    }


}