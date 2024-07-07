package com.softserve.edu.teachua.tests;

import com.softserve.edu.util.LoggerUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

class RunnerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testResult = context.getExecutionException().isPresent();
        LoggerUtils.logInfo("\t\tException.isPresent(): ", String.valueOf(testResult));
        LoggerUtils.logInfo("\t\tTest context.getDisplayName(): ", context.getDisplayName());
        TestRunner.isTestSuccessful = !testResult;
    }
}
