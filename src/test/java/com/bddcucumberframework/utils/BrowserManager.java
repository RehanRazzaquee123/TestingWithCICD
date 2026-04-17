package com.bddcucumberframework.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BrowserManager {

    private static final Logger log = LoggerHelper.getLogger();
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static void openUrl(String url) {
        initDriver();
        WebDriver driver = driverThread.get();
        log.info("Opening URL: {}", url);
        driver.get(url);
        log.info("Page title after navigation: {}", driver.getTitle());
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static String getPageTitle() {
        WebDriver driver = driverThread.get();
        return driver != null ? driver.getTitle() : "";
    }

    public static String getPageSource() {
        WebDriver driver = driverThread.get();
        return driver != null ? driver.getPageSource() : "";
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Error quitting browser: {}", e.getMessage());
            }
            driverThread.remove();
        }
        log.info("Browser session cleared.");
    }

    private static void initDriver() {
        if (driverThread.get() == null) {
            try {
                // Use Selenium Manager (built into Selenium 4.12+) to auto-manage ChromeDriver
                // This should work without WebDriverManager and download the driver automatically
                ChromeOptions options = new ChromeOptions();
                if (isHeadless()) {
                    options.addArguments("--headless=new");
                    options.addArguments("--disable-gpu");
                } else {
                    options.addArguments("--start-maximized");
                }
                options.addArguments("--remote-allow-origins=*");

                log.info("Initializing ChromeDriver with Selenium Manager...");
                WebDriver driver = new ChromeDriver(options);
                driverThread.set(driver);
                log.info("ChromeDriver initialized successfully");

                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            } catch (Exception e) {
                log.error("Failed to initialize ChromeDriver: {}", e.getMessage());
                throw new RuntimeException("Unable to initialize ChromeDriver", e);
            }
        }
    }

    private static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("browser.headless", "false"));
    }
}
