package com.softserve.edu.teachua.wraps.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchExplicitInvisibily extends SearchExplicitPresent {
    private int count = 0;

    @Override
    public boolean isInvisibily(WebElement element) {
        return getDriverWait().until(ExpectedConditions.stalenessOf(element));
    }

    @Override
    public boolean isInvisibilyCss(String cssSelector, String text) {
        return getDriverWait().until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));  // Updated for firefox
                        String firstElementText = elements.isEmpty() ? "" : elements.get(0).getText();
                        return !firstElementText.contains(text) || (count++ > 6);
                    }
                }
        );
    }
}
