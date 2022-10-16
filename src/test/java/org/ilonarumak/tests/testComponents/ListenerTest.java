package org.ilonarumak.tests.testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.ilonarumak.resources.ExtentReporterNG;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class ListenerTest extends BaseTest implements ITestListener {
    private ExtentTest test;
    private final ExtentReports extentReports = ExtentReporterNG.getReportObject();
    // get report object
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); // create a thread for each test

    @Override
    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getName()); // start looking into test
        extentTest.set(test); // give a unique thread id for each test
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable()); // return readable error
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String filePath = null;
        try {
            filePath = getScreenshot(result.getName(), driver); // create screenshot
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath, result.getName()); // attach to report
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
