package org.ilonarumak.tests.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTest implements IRetryAnalyzer {
    private int count = 0;
    private static final int maxTry = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (count < maxTry) {
            count++;
            return true;
        }
        return false;
    }
}
