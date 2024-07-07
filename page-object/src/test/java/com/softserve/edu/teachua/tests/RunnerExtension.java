package com.softserve.edu.teachua.tests;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

class RunnerExtension implements AfterTestExecutionCallback {

    protected final Logger logger = LogManager.getLogger(RunnerExtension.class);

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testResult = context.getExecutionException().isPresent();
        logger.info("\t\t\t\tException.isPresent() = " + testResult);
        logger.info("\t\t\t\tTest context.getDisplayName(): " + context.getDisplayName());
        TestRunner.isTestSuccessful = !testResult;
    }
}
