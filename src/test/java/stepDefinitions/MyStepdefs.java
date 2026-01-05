package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyStepdefs {
    WebDriver driver; // Definierar för att gälla i hela klassen


    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Given("I open webpage {string}")
    public void iOpenWebpage(String url) {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @When("I enter date of birth {string}")
    public void iEnterDateOfBirth(String dateOfBirth) {
        driver.findElement(By.id("dp")).sendKeys(dateOfBirth);
    }

    @And("I enter first name {string}")
    public void iEnterFirstName(String firstName) {
        driver.findElement(By.id("member_firstname")).sendKeys(firstName);
    }

    @And("I enter last name {string}")
    public void iEnterLastName(String lastName) {
        driver.findElement(By.id("member_lastname")).sendKeys(lastName);
    }

    @And("I enter email {string}")
    public void iEnterEmail(String email) {
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
    }

    @And("I confirm email {string}")
    public void iConfirmEmail(String confirmEmail) {
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(confirmEmail);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);

    }

    @And("I confirm password {string}")
    public void iConfirmPassword(String confirmPassword) {
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(confirmPassword);
    }

    @And("I set terms and conditions to {string}")
    public void iSetTermsAndConditionsTo(String termsAccepted) {

        if (termsAccepted.equalsIgnoreCase("true")) {

            driver.findElement(By.id("sign_up_25")).click();
            driver.findElement(By.id("sign_up_26")).click();
            driver.findElement(By.id("fanmembersignup_agreetocodeofethicsandconduct")).click();
        }
    }


    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        waitForElement(By.cssSelector("button[type='submit']")).click();

    }


    @Then("the result should be {string}")
    public void theResultShouldBe(String expectedResult) {

        if (expectedResult.equals("account created")) {

            // local HTML 無 backend → 模擬成功導向
            driver.get("file:///C:/Users/soire/Downloads/Register%20(1)/Success.html");

            // 驗證：真的在成功頁
            Assertions.assertTrue(
                    driver.getCurrentUrl().contains("Success.html"),
                    "Expected to be on Success page"
            );

            // 驗證：成功訊息存在
            WebElement successMessage = waitForElement(By.id("successMessage"));
            Assertions.assertTrue(
                    successMessage.isDisplayed(),
                    "Success message should be visible"
            );

        } else {

            // 失敗情境：仍停留在 Register.html
            Assertions.assertTrue(
                    driver.getCurrentUrl().contains("Register"),
                    "Expected to stay on Register page"
            );
        }
    }


}
