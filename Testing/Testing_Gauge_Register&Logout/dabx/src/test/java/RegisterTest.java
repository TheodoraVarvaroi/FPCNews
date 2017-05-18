import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegisterTest {
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

    @Step("Click on button <register> ")
    public void clickOnButton(String button){
        WebElement clickButton = driver.findElement(By.name(button));
        clickButton.click();
    }
    @Step("Check if register open <form>")
    public void openRegister(String casuta){
        WebElement casutaDeschisa = driver.findElement(By.tagName(casuta));
        Assert.assertTrue(casutaDeschisa.equals("form"));
    }

    @Step("Daca se apasa Register")
    public void ExistaCampurileInRegister(String username, String password, String confirmPassword, String email){
        WebElement usernameImput = driver.findElement(By.id("Username"));
        usernameImput.sendKeys(username);

        WebElement passwordImput = driver.findElement(By.id("Password"));
        passwordImput.sendKeys(password);

        WebElement confirmPasswordImput = driver.findElement(By.id("Confirm Password"));
        confirmPasswordImput.sendKeys(confirmPassword);

        WebElement emailImput = driver.findElement(By.id("Email"));
        emailImput.sendKeys(email);


            WebElement registerButton = driver.findElement(By.id("Register"));
            registerButton.click();

    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }
}