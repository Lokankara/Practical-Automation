package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.browser.UrlUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunnerExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestRunner {
    protected static final Logger logger = Logger.getLogger(TestRunner.class.getName());
    protected static boolean isTestSuccessful = false;

    @BeforeAll
    public void beforeAll() {
    }

    @AfterAll
    public void afterAll() {
        DriverUtils.getInstance().quitAll();
    }

    @BeforeEach
    public void beforeEach() {
//         SearchStrategy.setImplicitStrategy();
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        if (!isTestSuccessful) {
            logger.warn("\t\t\tTest_Name = " + testInfo.getDisplayName() + " fail");
            logger.warn("\t\t\tTest_Method = " + testInfo.getTestMethod() + " fail");
            DriverUtils.getInstance().takeScreenShot();
            DriverUtils.getInstance().takePageSource();
        }
        DriverUtils.getInstance().deleteCookies();
        DriverUtils.getInstance().deleteTokens();
        DriverUtils.getInstance().quit();
    }

    protected HomePage loadApplication() {
        DriverUtils.getInstance().getUrl(UrlUtils.getBaseUrl());
        return new HomePage();
    }
}
