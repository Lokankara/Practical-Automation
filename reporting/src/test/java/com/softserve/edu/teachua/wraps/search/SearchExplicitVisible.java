package com.softserve.edu.teachua.wraps.search;

import java.util.List;

import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchExplicitVisible extends SearchExplicit {

    @Override
    protected WebElement getWebElement(By by) {
        ReportUtils.logAction("Getting web element with visibility condition: " + by.toString());
        return getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Override
    protected WebElement getWebElement(By by, WebElement fromWebElement) {
        ReportUtils.logAction("Getting web element with visibility condition from parent: " + by.toString());
        return getWebElements(by, fromWebElement).get(0);
    }

    @Override
    protected List<WebElement> getWebElements(By by) {
        ReportUtils.logAction("Getting list of web elements with visibility condition: " + by.toString());
        return getDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    @Override
    protected List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        ReportUtils.logAction("Getting list of nested web elements with visibility condition: " + by.toString());
        return getDriverWait().until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(fromWebElement, by));
    }
}
