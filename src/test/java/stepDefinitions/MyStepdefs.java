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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class MyStepdefs {
    WebDriver driver; // Definierar för att gälla i hela klassen


    //explicit wait: Väntar tills ett element syns
    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //Stänger webbläsaren efter varje scenario
    @After
    public void teardown() {
        driver.quit();
    }

    @Given("I open webpage {string}")
    public void iOpenWebpage(String url) {

        // 1. Öppna i Chrome först
        driver = new ChromeDriver();
        driver.get(url);
        // kontroll att vi är på rätt sida
        Assertions.assertTrue(driver.getCurrentUrl().contains("Register.html"));

        // Stäng Chrome
        driver.quit();

        // 2. Öppna samma sida i Edge
        driver = new EdgeDriver();
        driver.get(url);
        // Enkel kontroll igen
        Assertions.assertTrue(driver.getCurrentUrl().contains("Register.html"));


    }

    @When("I enter date of birth {string}") //Fyller i födelsedatum
    public void iEnterDateOfBirth(String dateOfBirth) {
        driver.findElement(By.id("dp")).sendKeys(dateOfBirth);
    }

    @And("I enter first name {string}") //Fyller i förnamn
    public void iEnterFirstName(String firstName) {
        driver.findElement(By.id("member_firstname")).sendKeys(firstName);
    }

    @And("I enter last name {string}") //Fyller i efternamn
    public void iEnterLastName(String lastName) {
        driver.findElement(By.id("member_lastname")).sendKeys(lastName);
    }

    @And("I enter email {string}") //Fyller i e-post
    public void iEnterEmail(String email) {
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
    }

    @And("I confirm email {string}") //Bekräftar e-post
    public void iConfirmEmail(String confirmEmail) {
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(confirmEmail);
    }

    @And("I enter password {string}") //Fyller i lösenord
    public void iEnterPassword(String password) {
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);

    }

    @And("I confirm password {string}") //Bekräftar lösenord
    public void iConfirmPassword(String confirmPassword) {
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(confirmPassword);
    }

    @And("I set terms and conditions to {string}")
    public void iSetTermsAndConditionsTo(String termsAccepted) {

        // Om termsAccepted inte är "true" så klickar vi inte i checkboxarna
//        if (!termsAccepted.equalsIgnoreCase("true")) return;

        // Klickar i obligatoriska villkor
        clickLabelFor("sign_up_25");
        clickLabelFor("sign_up_26");
        clickLabelFor("fanmembersignup_agreetocodeofethicsandconduct");
    }

    private void clickLabelFor(String inputId) {
        driver.findElement(By.cssSelector("label[for='" + inputId + "']")).click();
    }

    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        waitForElement(By.cssSelector("input[type='submit'], button[type='submit']")).click();
    }

    @Then("the result should be {string}")
    public void theResultShouldBe(String expectedResult) {

        // Hämtar nuvarande URL
        String current = driver.getCurrentUrl();

        if (expectedResult.equals("account created")) {
            // Local HTML har ingen backend, därför går jag manuellt till Success.html
            String successUrl = current.replace("Register.html", "Success.html");
            driver.get(successUrl);

            // Väntar tills URL innehåller Success.html
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("Success.html"));

            // verifierar resultatet genom att kontrollera URL: om det är lyckat ska vi vara på Success.html, annars ska vi stanna på Register.html.
            // Verifierar att vi är på Success.html
            Assertions.assertTrue(driver.getCurrentUrl().contains("Success.html"));

        } else { //Vid fel ska vi stanna på Register.html
            Assertions.assertTrue(
                    driver.getCurrentUrl().contains("Register.html"));
        }
    }
}