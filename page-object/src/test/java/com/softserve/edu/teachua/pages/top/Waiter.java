package com.softserve.edu.teachua.pages.top;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public abstract class Waiter implements Interactions<WebElement> {

    protected WebDriver driver;
    protected WebDriverWait driverWait;
    protected static final Duration EXPLICITLY_WAIT = Duration.ofSeconds(10L);
    protected static final Duration IMPLICITLY_WAIT = Duration.ofSeconds(5L);

    public Waiter(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        driverWait = new WebDriverWait(driver, EXPLICITLY_WAIT);
    }

    protected void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    protected WebDriverWait getWait() {
        return driverWait;
    }

    @Override
    public WebElement waitForElementToBeClickable(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    @Override
    public WebElement waitForPresenceOfElementLocated(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    @Override
    public WebElement waitForVisibilityOfElement(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    @Override
    public WebElement waitForVisibilityOfElement(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    @Override
    public List<WebElement> waitForVisibilityOfAllElements(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
    }

    @Override
    public List<WebElement> waitForVisibilityOfAllElements(List<WebElement> elements) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfAllElements(elements)));
    }

    @Override
    public List<WebElement> waitForPresenceOfAllElements(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)));
    }

    @Override
    public Boolean waitForTextToBePresentInElementLocated(WebElement element, String text) {
        return getWait().until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    @Override
    public Boolean waitForTextToBePresentInElementLocated(By locator, String text) {
        return getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    @Override
    public List<WebElement> waitMoreThanExpectedSize(By locator, int size) {
        return applyWait(wait -> wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, size)));
    }

    @Override
    public List<WebElement> waitUntilExpectedSize(By locator, int expectedSize) {
        return applyWait(wait -> wait.until(ExpectedConditions.numberOfElementsToBe(locator, expectedSize)));
    }

    @Override
    public Boolean waitForInvisibilityOfElement(By locator) {
        return applyWait(wait -> driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator)));
    }

    @Override
    public Boolean waitForInvisibilityOfElement(WebElement element) {
        return applyWait(wait -> driverWait.until(ExpectedConditions.invisibilityOf(element)));
    }

    @Override
    public Boolean waitForCurrentUrl(String url) {
        return applyWait(wait -> driverWait.until(ExpectedConditions.urlToBe(url)));
    }

    @Override
    public void waitStalenessOf(WebElement element) {
        applyWait(wait -> wait.until(ExpectedConditions.stalenessOf(element)));
    }

    private <T> T applyWait(Function<WebDriverWait, T> condition) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        T result = condition.apply(driverWait);
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        return result;
    }
}
