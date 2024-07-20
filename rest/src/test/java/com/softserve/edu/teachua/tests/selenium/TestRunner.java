package com.softserve.edu.teachua.tests.selenium;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.browser.UrlUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunnerExtension.class)
public abstract class TestRunner {

    private static final Long ONE_SECOND_DELAY = 1000L;
    protected static boolean isTestSuccessful = false;

    // Overload
    protected static void presentationSleep() {
        presentationSleep(1);
    }

    // Overload
    protected static void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
        presentationSleep(); // For Presentation ONLY
        DriverUtils.quit();
    }

    @BeforeEach
    public void beforeEach() {
        presentationSleep(); // For Presentation ONLY
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        if (!isTestSuccessful) {
            // log
            System.out.println("\t\t\tTest_Name = " + testInfo.getDisplayName() + " fail");
            System.out.println("\t\t\tTest_Method = " + testInfo.getTestMethod() + " fail");
            DriverUtils.takeScreenShot();
            DriverUtils.takePageSource();
        }
        DriverUtils.deleteCookies();
        DriverUtils.deleteTokens();
        presentationSleep(); // For Presentation ONLY
        //
    }

    protected HomePage loadApplication() {
        DriverUtils.getUrl(UrlUtils.getBaseUrl());
        return new HomePage();
    }
}
