import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    private WebDriver driver;

    @BeforeScenario
    public void setup() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Onu\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();
    }


    @Step("Navigate to <url>")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("Click on button <Button> ")
    public void clickOnButton(String button){
        WebElement clickButton = driver.findElement(By.name(button));
        clickButton.click();
    }
    @Step("Close pop`up")
    public void closeButton(){
        WebElement clickCloseButton = driver.findElement(By.className("close"));
        clickCloseButton.click();
    }


    @Step("Page title should be <The Bookery>")
    public void pageTitleShouldBe(String title) {
        Assert.assertFalse(title.equals(driver.getTitle().substring(0,3)));

    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }
}
