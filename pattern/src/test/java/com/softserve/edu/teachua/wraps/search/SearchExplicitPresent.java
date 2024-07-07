package com.softserve.edu.teachua.wraps.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchExplicitPresent extends SearchExplicit {

    @Override
    public WebElement getWebElement(By by) {
        return applyWait(wait -> wait.until(ExpectedConditions.presenceOfElementLocated(by)));
    }

    @Override
    public WebElement getWebElement(By by, WebElement fromWebElement) {
        return applyWait(wait -> wait.until(driver -> fromWebElement.findElement(by)));
    }

    @Override
    public List<WebElement> getWebElements(By by) {
        return applyWait(wait -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
    }

    @Override
    public List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        return applyWait(wait -> wait.until(driver -> fromWebElement.findElements(by)));
    }
}
