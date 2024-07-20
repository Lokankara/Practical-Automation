package com.softserve.edu.teachua.wraps.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class SearchExplicitExistFirstText extends SearchExplicitPresent {

    @Override
    public boolean isLocated(By by) {
        return getDriverWait().until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        List<WebElement> elements = webElements(by);
                        System.out.println("\telements.get(0).getText() = " + elements.get(0).getText());
                        return !elements.get(0).getText().isEmpty();
                    }
                }
        );
    }
}
