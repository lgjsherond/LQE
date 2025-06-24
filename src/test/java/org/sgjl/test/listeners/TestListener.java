package org.sgjl.test.listeners;

import io.qameta.allure.Attachment;
import org.sgjl.BasePage;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BasePage implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        attachScreenshot();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return takeScreenshot();
    }
}
