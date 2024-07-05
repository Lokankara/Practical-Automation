package com.softserve.edu.teachua.wraps.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchExplicitPresent extends SearchExplicit {

    @Override
    protected WebElement getWebElement(By by) {
        return getDriverWait().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Override
    protected WebElement getWebElement(By by, WebElement fromWebElement) {
        return getDriverWait().until(ExpectedConditions.presenceOfNestedElementLocatedBy(fromWebElement, by));
    }

    @Override
    protected List<WebElement> getWebElements(By by) {
        return getDriverWait().until((ExpectedCondition<List<WebElement>>) driver -> {
            List<WebElement> allChildren = driver.findElements(by);
            return allChildren.isEmpty() ? null : allChildren;
        }
        );
    }

    @Override
    protected List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        return getDriverWait().until((ExpectedCondition<List<WebElement>>) driver -> {
            List<WebElement> allChildren = fromWebElement.findElements(by);
            return allChildren.isEmpty() ? null : allChildren;
        }
        );
    }

}
