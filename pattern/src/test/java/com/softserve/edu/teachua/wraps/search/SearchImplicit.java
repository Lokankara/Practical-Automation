package com.softserve.edu.teachua.wraps.search;

import java.util.List;

import com.softserve.edu.teachua.exceptions.InvalidPropertyException;
import com.softserve.edu.teachua.tools.PropertiesUtils;
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
        try {
            propertyWaitSeconds = Long.parseLong(PropertiesUtils.get().readImplicitWait());
        } catch (NumberFormatException e) {
            logger.error(String.format("ERROR Reading Implicitly properties. Use default: %d. Message = %s", DEFAULT_IMPLICIT_WAIT_SECONDS, e.getMessage()));
            throw new InvalidPropertyException("Invalid explicit wait property value", e);
        } finally {
            DriverUtils.getInstance().setImplicitWait(propertyWaitSeconds);
            logger.info("Using SearchImplicit");
        }
    }

    @Override
    public WebElement getWebElement(By by) {
        return DriverUtils.getInstance().getDriver().findElement(by);
    }

    @Override
    public WebElement getWebElement(By by, WebElement fromWebElement) {
        return fromWebElement.findElement(by);
    }

    @Override
    public List<WebElement> getWebElements(By by) {
        return DriverUtils.getInstance().getDriver().findElements(by);
    }

    @Override
    public List<WebElement> getWebElements(By by, WebElement fromWebElement) {
        return fromWebElement.findElements(by);
    }
}
