package com.bddcucumberframework.stepdefinitions;

import com.bddcucumberframework.pages.MyntraHomePage;
import com.bddcucumberframework.utils.BrowserManager;
import com.bddcucumberframework.utils.ExtentReportManager;
import com.bddcucumberframework.utils.LoggerHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;

public class UiStepDefinitions {

    private static final Logger log = LoggerHelper.getLogger();
    private MyntraHomePage myntraHomePage;

    @Given("the user opens Myntra")
    public void the_user_opens_myntra() {
        ExtentReportManager.getTest().info("Opening Myntra website");
        log.info("Launching Myntra homepage");
        BrowserManager.openUrl("https://www.myntra.com");
        myntraHomePage = new MyntraHomePage();
        assertTrue(myntraHomePage.isPageLoaded(), "Myntra homepage did not load correctly.");
    }
    @Given("the user opens Ministry of Testing")
    public void the_user_opens_MinistryofTesting() {
        ExtentReportManager.getTest().info("Opening MinistryofTesting website");
        log.info("Launching MinistryofTesting homepage");
        BrowserManager.openUrl("https://club.ministryoftesting.com/");
        myntraHomePage = new MyntraHomePage();
        assertTrue(myntraHomePage.isPageLoaded(), "MinistryofTesting homepage did not load correctly.");
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        String actualTitle = myntraHomePage.getPageTitle();
        log.info("Verifying page title contains '{}', actual title='{}'", expectedTitle, actualTitle);
        ExtentReportManager.getTest().info("Page title: " + actualTitle);
        assertTrue(actualTitle.contains(expectedTitle), "Title did not contain expected text.");
    }

    @And("the page should display text {string}")
    public void the_page_should_display_text(String expectedText) {
        log.info("Verifying page contains text '{}'.", expectedText);
        ExtentReportManager.getTest().info("Searching page text: " + expectedText);
        assertTrue(myntraHomePage.hasText(expectedText), "Page did not contain expected text.");
    }

    @And("the browser is closed")
    public void the_browser_is_closed() {
        log.info("Closing browser window");
        ExtentReportManager.getTest().info("Closing browser");
        BrowserManager.quitDriver();
    }
}
