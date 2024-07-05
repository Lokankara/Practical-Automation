package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SearchExplicitExistText extends SearchExplicitPresent {

    @Override
    public boolean isLocatedCss(String cssSelector) {
        return getDriverWait().until(
                (ExpectedCondition<Boolean>) driver -> {
                    WebElement popup = cssSelector(cssSelector);
                    ReportUtils.logInfo("Text found in element with CSS selector '" + cssSelector + "': " + popup.getText());
                    return !popup.getText().isEmpty();
                }
        );
    }
}
