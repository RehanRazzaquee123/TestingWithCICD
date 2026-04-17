package com.bddcucumberframework.pages;

import com.bddcucumberframework.utils.BrowserManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage() {
        this.driver = BrowserManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public WebElement getUsernameField() {
        return driver.findElement(By.id("username"));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.id("password"));
    }

    public WebElement getSubmitButton() {
        return driver.findElement(By.id("submit"));
    }

    public void enterUsername(String username) {
        getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        getPasswordField().sendKeys(password);
    }

    public void clickSubmitButton() {
        getSubmitButton().click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
