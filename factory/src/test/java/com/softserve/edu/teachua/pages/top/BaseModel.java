package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.util.Configuration;
import com.softserve.edu.util.DriverWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseModel {

    private final DriverWrapper driverWrapper;

    public BaseModel(WebDriver driver) {
        driverWrapper = new DriverWrapper(driver, Configuration.getInstance().getImplicitWait());
        PageFactory.initElements(driver, this);
    }

    protected boolean isCurrentURLContains(String url) {
        return driverWrapper.waitForElementWithUrl(url);
    }

    protected WebElement waitForPresenceOfElementLocated(By locator) {
        return driverWrapper.presenceOfElementLocated(locator);
    }

    protected void waitAndType(By locator, String text) {
        driverWrapper.waitAndType(locator, text);
    }

    protected WebElement waitForClickableOfElement(By locator) {
        return driverWrapper.clickableElement(locator);
    }

    protected WebElement waitForClickableOfElement(WebElement element) {
        return driverWrapper.waitForClickableOfElement(element);
    }

    protected void waitFrameToBeAvailableAndSwitchToIt(By locator) {
        driverWrapper.moveToFrame(getDriver().findElement(locator));
    }

    protected WebDriver getDriver() {
        return driverWrapper.getDriver();
    }

    protected void scrollTo(WebElement element) {
        driverWrapper.scrollToElement(element);
    }

    protected void fillInput(By input, String value) {
        driverWrapper.fillInput(input, value);
    }

    protected void fillAmount(WebElement amountInput, String amount) {
        driverWrapper.enterAmount(amountInput, amount);
    }

    protected void gotoNext() {
        driverWrapper.moveToNext();
    }

    protected void gotoPrev() {
        driverWrapper.moveToPrev();
    }
}
