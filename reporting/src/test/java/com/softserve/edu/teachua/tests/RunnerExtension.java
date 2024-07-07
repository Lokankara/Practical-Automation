package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.tools.ReportUtils;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RunnerExtension implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        AfterAllCallback,
        TestExecutionExceptionHandler,
        TestWatcher {

    private long startTime;

    private static long startTimeAll;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Step("Before all tests execution")
    public void beforeAll(ExtensionContext context) {
        startTimeAll = System.currentTimeMillis();
        String formattedStartTime = LocalDateTime.now().format(formatter);
        ReportUtils.logTestStart("All tests started at " + formattedStartTime);
    }

    @Override
    @Step("After all tests execution")
    public void afterAll(ExtensionContext context) {
        long endTimeAll = System.currentTimeMillis();
        String formattedEndTime = LocalDateTime.now().format(formatter);
        ReportUtils.logTestEnd("All tests ended at " + formattedEndTime);
        ReportUtils.logTestEnd("Total duration for all tests: " + (endTimeAll - startTimeAll) / 1000 + " seconds");
    }

    @Override
    @Step("Before test: {displayName}")
    public void beforeTestExecution(ExtensionContext context) {
        startTime = System.currentTimeMillis();
        String formattedStartTime = LocalDateTime.now().format(formatter);
        ReportUtils.logTestStart("Test started: " + context.getDisplayName() + " at " + formattedStartTime);
    }

    @Override
    @Step("After execution of test: {displayName}")
    public void afterTestExecution(ExtensionContext context) {
        long endTime = System.currentTimeMillis();
        boolean testResult = context.getExecutionException().isPresent();
        String formattedEndTime = LocalDateTime.now().format(formatter);

        ReportUtils.logTestEnd("Test ended: " + context.getDisplayName() + " at " + formattedEndTime);
        ReportUtils.logTestEnd("Test duration: " + context.getDisplayName() + " took " + (endTime - startTime) + " milliseconds");
        ReportUtils.logTestEnd("Test " + (testResult ? "FAILED" : "SUCCESS") + ": " + context.getDisplayName());
        TestRunner.isTestSuccessful = !testResult;
    }

    @Override
    @Step("Test passed: {displayName}")
    public void testSuccessful(ExtensionContext context) {
        ReportUtils.logTestEnd(context.getDisplayName());
    }

    @Override
    @Step("Test aborted: {displayName}, Reason: {causeMessage}")
    public void testAborted(ExtensionContext context, Throwable cause) {
        ReportUtils.logError("Test aborted: " + context.getDisplayName());
        ReportUtils.logError("Error message: " + cause.getMessage());
    }

    @Override
    @Step("Test failed: {displayName}, Reason: {causeMessage}")
    public void testFailed(ExtensionContext context, Throwable cause) {
        ReportUtils.logError("Test failed: " + context.getDisplayName());
        ReportUtils.logError("Error message: " + cause.getMessage());
    }

    @Override
    @Step("Test execution failed: {displayName}, Reason: {causeMessage}")
    public void handleTestExecutionException(ExtensionContext context, Throwable cause) throws Throwable {
        ReportUtils.logError("Exception occurred in test: " + context.getDisplayName());
        ReportUtils.logError("Exception details: " + cause.getMessage());
        TestRunner.isTestSuccessful = false;
        throw cause;
    }

}
