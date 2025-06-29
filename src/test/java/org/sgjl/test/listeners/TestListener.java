package org.sgjl.test.listeners;

import io.qameta.allure.Attachment;
import org.sgjl.BasePage;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtil;

public class TestListener extends BasePage implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerUtil.info("Test Failed: " + result.getName());
        attachScreenshot();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerUtil.info("Test Passed: " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.info("Test Started: " + result.getName());
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        return takeScreenshot();
    }
}
