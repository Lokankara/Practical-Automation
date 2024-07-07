package com.softserve.edu.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class DriverWrapper {

    private final Actions actions;
    protected final int IMPLICITLY_WAIT;
    private final JavascriptExecutor js;
    private final ThreadLocal<WebDriver> threadDriver;
    private final ThreadLocal<Wait<WebDriver>> threadWait;

    public DriverWrapper(WebDriver driver, int second) {
        threadDriver = new ThreadLocal<>();
        threadDriver.set(driver);
        threadWait = new ThreadLocal<>();
        IMPLICITLY_WAIT = Configuration.getInstance().getImplicitWait();
        threadWait.set(new WebDriverWait(driver, Duration.ofSeconds(second)));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public WebDriver getDriver() {
        return threadDriver.get();
    }

    public void moveToPrev() {
        actions.sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB)).perform();
    }

    public void moveToNext() {
        actions.sendKeys(Keys.TAB).perform();
    }

    public void fillInput(By locator, String value) {
        js.executeScript("arguments[0].setAttribute('value', arguments[1])", getDriver().findElement(locator), value);
    }

    public void scrollToElement(WebElement element) {
        applyWait((driver, locator) -> {
            threadWait.get().until(ExpectedConditions.visibilityOf(element));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            return null;
        }, null);
    }

    public void moveToFrame(WebElement frame) {
        applyWait((driver, locator) -> {
            threadWait.get().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
            return null;
        }, null);
    }

    public void enterAmount(WebElement amountInput, String amount) {
        js.executeScript("arguments[0].value = arguments[1];", amountInput, amount);
    }

    public void waitAndType(WebElement element, String text) {
        applyWait((driver, locator) -> {
            threadWait.get().until(ExpectedConditions.elementToBeClickable(element));
            element.clear();
            element.sendKeys(text);
            return null;
        }, null);
    }

    public void waitAndType(By selector, String text) {
        waitAndType(getDriver().findElement(selector), text);
    }

    public WebElement clickableElement(By locator) {
        return apply(ExpectedConditions.elementToBeClickable(locator), locator);
    }

    public WebElement presenceOfElementLocated(By locator) {
        return apply(ExpectedConditions.presenceOfElementLocated(locator), locator);
    }

    public boolean waitForElementWithUrl(String url) {
        return applyWait(wait -> wait.until(ExpectedConditions.urlContains(url)));
    }

    public WebElement waitForClickableOfElement(WebElement element) {
        return applyWait(wait -> wait.until(ExpectedConditions.elementToBeClickable(element)));
    }

    private WebElement apply(ExpectedCondition<WebElement> expectedCondition, By locator) {
        return applyWait((driver, selector) -> {
            threadWait.get().until(expectedCondition);
            return driver.findElement(selector);
        }, locator);
    }

    private <T> T applyWait(Function<WebDriverWait, T> condition) {
        threadDriver.get().manage().timeouts().implicitlyWait(Duration.ZERO);
        T result = condition.apply((WebDriverWait) threadWait.get());
        threadDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT));
        return result;
    }

    private <T> T applyWait(Waitable<T> waitable, By locator) {
        threadDriver.get().manage().timeouts().implicitlyWait(Duration.ZERO);
        T result = waitable.apply(threadDriver.get(), locator);
        threadDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT));
        return result;
    }
}
