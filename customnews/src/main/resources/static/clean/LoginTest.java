import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;


public class LoginTest {
    private WebDriver driver;

    @BeforeScenario
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }

    @Step("Navigate to <C:\\Users\\Jitca Beniamin\\Downloads\\login.html>")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("Log in with credentials <beni> and <1234>")
    public void login_with(String username, String password) {
        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
    }

    @Step("The dashboard should be displayed")
    public void check_dashboard_displayed() {
        WebElement dashboard = driver.findElement(By.linkText("Dashboard"));
        Assert.assertTrue(dashboard.isDisplayed());
    }

}

