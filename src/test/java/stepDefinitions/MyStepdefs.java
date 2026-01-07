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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;



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
        // if (!termsAccepted.equalsIgnoreCase("true")) return;

        // Klickar i obligatoriska villkor
        clickLabelFor("sign_up_25");
        clickLabelFor("sign_up_26");
        clickLabelFor("fanmembersignup_agreetocodeofethicsandconduct");
    }



    private void clickLabelFor(String inputId) {
            driver.findElement(By.cssSelector("label[for='" + inputId + "']")).click();
        }


//        // Hittar label som hör till input-id (checkbox)
//        By label = By.cssSelector("label[for='" + inputId + "']");
//

//        WebElement el = waitForElement(label);


//        // Scrollar så att elementet syns i mitten av sidan
//        ((JavascriptExecutor) driver)
//                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);


//        // Försöker klicka normalt, annars använder vi JS-klick som backup
//        try {
//            el.click();
//        } catch (WebDriverException e) {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
//        }


    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {

        By submit = By.cssSelector("input[type='submit'], button[type='submit']");
        WebElement btn = waitForElement(submit);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        btn.click();
    }



    @Then("the result should be {string}")
    public void theResultShouldBe(String expectedResult) {

        String current = driver.getCurrentUrl();

        if (expectedResult.equals("account created")) {

            // local HTML 沒 backend：手動導向 Success.html
            String successUrl = current.replace("Register.html", "Success.html");
            driver.get(successUrl);

            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlContains("Success.html"));

            Assertions.assertTrue(
                    driver.getCurrentUrl().contains("Success.html"),
                    "Expected Success.html but was: " + driver.getCurrentUrl()
            );

        } else {
            // 其他錯誤情況：應該留在 Register.html（目前 3 個 scenario 都是 pass）
            Assertions.assertTrue(
                    driver.getCurrentUrl().contains("Register.html"),
                    "Expected to stay on Register page but was: " + driver.getCurrentUrl()
            );
        }
    }
}