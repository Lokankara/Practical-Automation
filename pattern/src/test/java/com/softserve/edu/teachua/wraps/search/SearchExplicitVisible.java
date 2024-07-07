package com.softserve.edu.teachua.wraps.search;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchExplicitVisible extends SearchExplicit {

    @Override
    public WebElement getWebElement(By by) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
    }

    @Override
    public WebElement getWebElement(By by, WebElement fromWebElement) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOf(fromWebElement.findElement(by))));
    }

    @Override
    public List<WebElement> getWebElements(By by) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by)));
    }

    @Override
    public List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(fromWebElement, by)));
    }
}
