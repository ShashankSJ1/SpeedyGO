package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class Login {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl = "http://localhost:3000"; // Update to match your React login URL

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        // Set up Selenium WebDriver (ensure you have the correct chromedriver version)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Shashank.sj\\OneDrive - Happiest Minds Technologies Limited\\Desktop\\speedygo_backend\\SelniumTest\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Increased timeout

        // Navigate to the login page
        driver.get(baseUrl);
        System.out.println("Login Page Loaded: " + baseUrl);
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        // Locate and fill in the email and password fields
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='email']")));
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Enter valid credentials
        emailField.sendKeys("xyz123@gmail.com");
        passwordField.sendKeys("newPassword123");

        // Click the login button
        loginButton.click();
    }

    @Then("the user should be redirected to the dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        // Check that the user is redirected to the dashboard (update based on your actual dashboard URL)
        wait.until(ExpectedConditions.urlContains("/transporter/home"));
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:3000/transporter/home", currentUrl); // Adjust to the correct dashboard URL
        System.out.println("User successfully logged in and redirected to dashboard.");
    }

    @When("the user enters invalid credentials")
    public void the_user_enters_invalid_credentials() {
        // Locate and fill in invalid email and password fields
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='email']")));
        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Enter invalid credentials
        emailField.sendKeys("invalidemail@gmail.com");
        passwordField.sendKeys("InvalidPass@123");

        // Click the login button
        loginButton.click();
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_message() {
        // Verify the error message for invalid credentials
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-error")));
        assertEquals("Invalid username or password", errorMessage.getText());
        System.out.println("User sees an error message: Invalid username or password.");
    }

    @When("the user tries to login without entering credentials")
    public void the_user_tries_to_login_without_entering_credentials() {
        // Click the login button without entering any credentials
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();
    }

    @Then("the user should see a validation message")
    public void the_user_should_see_a_validation_message() {
        // Verify the validation message for empty credentials
        WebElement validationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-error")));
        assertEquals("Email is required", validationMessage.getText()); // Update with actual validation message
        System.out.println("User sees a validation message: Please enter username and password.");
    }

    // Tear down Selenium WebDriver after each scenario
    @io.cucumber.java.After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }
}
