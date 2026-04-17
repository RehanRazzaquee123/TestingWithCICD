package com.bddcucumberframework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static final ExtentReports extentReports = createInstance("test-output/extent-report.html");
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setDocumentTitle("BDD Cucumber Test Report");
        htmlReporter.config().setReportName("BDD Cucumber Framework Report");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

    public static ExtentTest createTest(String name) {
        ExtentTest test = extentReports.createTest(name);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReports() {
        extentReports.flush();
    }
}
