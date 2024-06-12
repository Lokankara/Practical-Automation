package com.softserve.edu.driver;

import com.softserve.edu.manger.CustomWebDriverListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

public abstract class AbstractDriverFactory implements DriverFactory {

    protected boolean headless = true;

    public WebDriver getDriver() {
        WebDriverListener listener = new CustomWebDriverListener();
        return new EventFiringDecorator<>(listener).decorate(createDriver());
    }

    protected abstract WebDriver createDriver();

    public DriverFactory setHeadless(boolean isHeadless) {
        this.headless = isHeadless;
        return this;
    }
}
