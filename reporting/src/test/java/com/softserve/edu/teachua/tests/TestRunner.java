package com.softserve.edu.teachua.tests;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.browser.UrlUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RunnerExtension.class)
public abstract class TestRunner {

    private static final Long ONE_SECOND_DELAY = 1000L;
    protected static boolean isTestSuccessful = false;

    protected static void presentationSleep() {
        presentationSleep(1);
    }

    protected static void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY);
        } catch (InterruptedException e) {
            ReportUtils.logError(e.getMessage());
        }
    }

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
        presentationSleep();
        DriverUtils.quit();
    }

    @BeforeEach
    public void beforeEach() {
        // SearchStrategy.setImplicitStrategy();
        presentationSleep(); // For Presentation ONLY
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        if (!isTestSuccessful) {
            ReportUtils.logError("Test Name = " + testInfo.getDisplayName() + " failed");
            ReportUtils.logError("Test Method = " + testInfo.getTestMethod().orElse(null) + " failed");
            DriverUtils.takeScreenShot();
            DriverUtils.takePageSource();
        }
        ReportUtils.logAction("Performing cleanup actions: logout, clear cache");
        DriverUtils.deleteCookies();
        ReportUtils.logAction("Deleted cookies");
        DriverUtils.deleteTokens();
        ReportUtils.logAction("Deleted tokens");
    }

    protected HomePage loadApplication() {
        String baseUrl = UrlUtils.getBaseUrl();
        ReportUtils.logAction("Loading application at URL: " + baseUrl);
        DriverUtils.getUrl(baseUrl);
        ReportUtils.logAction("Application loaded successfully at URL: " + baseUrl);
        return new HomePage();
    }
}
