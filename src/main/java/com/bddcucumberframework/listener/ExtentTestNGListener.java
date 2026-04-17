package com.bddcucumberframework.listener;

import com.bddcucumberframework.utils.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

public class ExtentTestNGListener extends TestListenerAdapter {

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReports();
    }
}
