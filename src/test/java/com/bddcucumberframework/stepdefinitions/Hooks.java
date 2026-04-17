package com.bddcucumberframework.stepdefinitions;

import com.bddcucumberframework.utils.BrowserManager;
import com.bddcucumberframework.utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
        ExtentReportManager.getTest().info("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentReportManager.getTest().fail("Scenario failed: " + scenario.getName());
        } else {
            ExtentReportManager.getTest().pass("Scenario passed: " + scenario.getName());
        }
        BrowserManager.quitDriver();
    }
}
