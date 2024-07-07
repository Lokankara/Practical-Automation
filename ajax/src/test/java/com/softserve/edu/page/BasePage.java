package com.softserve.edu.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    private final WebDriver driver;
    private final Wait<WebDriver> wait;
    protected static final Long IMPLICITLY_WAIT_SECONDS = 2L;
    private static final Long EXPLICITLY_WAIT_SECONDS = 5L;

    protected BasePage(final WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITLY_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public Wait<WebDriver> getWait() {
        return wait;
    }

    protected WebElement getElementByXpath(String xpath) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        return driver.findElement(By.xpath(xpath));
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void assertPresentText(WebElement element) {
        Assertions.assertNotNull(element, "Element should not be null: " + element);
        Assertions.assertNotNull(element.getText(), "Element text should not be null: " + element);
    }

    protected WebElement assertClickable(WebElement element) {
        Assertions.assertNotNull(element, "Element should not be null: " + element);
        Assertions.assertTrue(element.isEnabled(), "Element should be clickable: " + element);

        return element;
    }
}
