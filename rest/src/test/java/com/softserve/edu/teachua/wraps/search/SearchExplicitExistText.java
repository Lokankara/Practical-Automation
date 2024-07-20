package com.softserve.edu.teachua.wraps.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SearchExplicitExistText extends SearchExplicitPresent {

    @Override
    public boolean isLocatedCss(String cssSelector) {
        return getDriverWait().until(
                new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        WebElement popup = cssSelector(cssSelector);
                        System.out.println("\tpopup.getText() = " + popup.getText());
                        return !popup.getText().isEmpty();
                    }
                }
        );
    }

}
