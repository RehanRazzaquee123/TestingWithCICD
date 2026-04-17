package com.bddcucumberframework.stepdefinitions;
import com.bddcucumberframework.utils.PropertiesUtils;
import com.bddcucumberframework.pages.LoginPage;
import com.bddcucumberframework.pages.MyntraHomePage;
import com.bddcucumberframework.pages.OTCHomePage;
import com.bddcucumberframework.utils.BrowserManager;
import com.bddcucumberframework.utils.ExtentReportManager;
import com.bddcucumberframework.utils.LoggerHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginpage;
    private static final Logger log = LoggerHelper.getLogger();

    @Given("^I navigate to the login page$")
    public void navigateToLoginPage() {
        String url = PropertiesUtils.readProperty("rt.url", "src\\test\\resources\\config.properties");
        ExtentReportManager.getTest().info("Opening Myntra website");
        log.info("Launching Myntra homepage");
        BrowserManager.openUrl(url);
        loginpage = new LoginPage();
        Assert.assertEquals(loginpage.getPageTitle(), "OTC Trading Application");
    }

    @When("^I enter the username \"([^\"]*)\"$")
    public void enterUsername(String username) {
       loginpage.enterUsername(username);
    }

    @When("^I enter the password \"([^\"]*)\"$")
    public void enterPassword(String password) {
        loginpage.enterPassword(password);
    }
        

    @When("^I click the submit button$")
    public void clickSubmitButton() {
        loginpage.clickSubmitButton();
    }

    @Then("^I should be logged in successfully$")
    public void shouldBeLoggedIn() {
        OTCHomePage homePage = new OTCHomePage();
        Assert.assertTrue(homePage.isPageLoaded(), "Home page did load successfully after login.");
    }

    @Then("^I should see an error message$")
    public void shouldSeeErrorMessage() {
        // Step definition for the "Then" step
        // Add your code here to verify error message
    }
}