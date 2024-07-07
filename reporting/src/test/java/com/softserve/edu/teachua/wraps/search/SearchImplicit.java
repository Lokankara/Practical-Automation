package com.softserve.edu.teachua.wraps.search;

import java.util.List;

import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchImplicit extends Search {
    private static final long DEFAULT_IMPLICIT_WAIT_SECONDS = 10L;

    public SearchImplicit() {
        initImplicit();
    }

    private void initImplicit() {
        long propertyWaitSeconds = DEFAULT_IMPLICIT_WAIT_SECONDS;
        String propertyWait = PropertiesUtils.get().readImplicitWait();
        try {
            propertyWaitSeconds = Long.parseLong(propertyWait);
        } catch (NumberFormatException e) {
            ReportUtils.logError("Error reading implicit wait properties. Using default: " + DEFAULT_IMPLICIT_WAIT_SECONDS + ". Message: " + e.getMessage());

        }
        DriverUtils.setImplicitWait(propertyWaitSeconds);
        ReportUtils.logInfo("Using SearchImplicit. Implicit wait set to: " + propertyWaitSeconds + " seconds.");
    }

    @Override
    protected WebElement getWebElement(By by) {
        ReportUtils.logInfo("Finding web element by: " + by.toString());
        return DriverUtils.getDriver().findElement(by);
    }

    @Override
    protected WebElement getWebElement(By by, WebElement fromWebElement) {
        ReportUtils.logInfo("Finding web element by: " + by.toString() + " from parent element");
        return fromWebElement.findElement(by);
    }

    @Override
    protected List<WebElement> getWebElements(By by) {
        ReportUtils.logInfo("Finding list of web elements by: " + by.toString());
        return DriverUtils.getDriver().findElements(by);
    }

    @Override
    protected List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        ReportUtils.logInfo("Finding list of nested web elements by: " + by.toString() + " from parent element");
        return fromWebElement.findElements(by);
    }
}
