package com.softserve.edu.teachua.wraps.search;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SearchExplicitVisible extends SearchExplicit {

    @Override
    protected WebElement getWebElement(By by) {
        return getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Override
    protected WebElement getWebElement(By by, WebElement fromWebElement) {
        return getWebElements(by, fromWebElement).get(0);
    }

    @Override
    protected List<WebElement> getWebElements(By by) {
        return getDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    @Override
    protected List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        return getDriverWait().until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(fromWebElement, by));
    }

}
