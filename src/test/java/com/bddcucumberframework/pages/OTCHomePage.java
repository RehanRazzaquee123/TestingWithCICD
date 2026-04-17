package com.bddcucumberframework.pages;

import com.bddcucumberframework.utils.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OTCHomePage {

    private final WebDriver driver;

    @FindBy(css = "header")
    private WebElement header;

    public OTCHomePage() {
        this.driver = BrowserManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isPageLoaded() {
        return header != null && header.isDisplayed();
    }

    public boolean hasText(String expectedText) {
        return driver.getPageSource().contains(expectedText);
    }
}

