package com.bddcucumberframework.stepdefinitions;

import com.bddcucumberframework.utils.ExtentReportManager;
import com.bddcucumberframework.utils.LoggerHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertEquals;

public class StepDefinitions {

    private static final Logger log = LoggerHelper.getLogger();
    private String response;

    @Given("the application is available")
    public void the_application_is_available() {
        log.info("Checking application availability.");
        ExtentReportManager.getTest().info("Checking application availability.");
    }

    @When("I request a welcome message")
    public void i_request_a_welcome_message() {
        log.info("Requesting welcome message.");
        response = "Hello from BDD Cucumber";
        ExtentReportManager.getTest().info("Welcome message requested.");
    }

    @Then("I should receive {string}")
    public void i_should_receive(String expectedMessage) {
        log.info("Verifying welcome message.");
        assertEquals(response, expectedMessage, "The welcome message should match the expected value.");
        ExtentReportManager.getTest().pass("Verified welcome message: " + response);
    }
}
