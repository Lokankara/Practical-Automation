package com.softserve.edu.driver;

import com.softserve.edu.manager.Configuration;
import com.softserve.edu.manager.CustomWebDriverListener;
import com.softserve.edu.reporter.LoggerUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

public abstract class AbstractDriverFactory implements DriverFactory {
    protected boolean headless = Configuration.getInstance().isHeadless();

    public WebDriver getDriver() {
        LoggerUtils.logInfo("headless", String.valueOf(this.headless));
        return new EventFiringDecorator<>(
                new CustomWebDriverListener())
                .decorate(create());
    }

    protected abstract WebDriver create();

    protected abstract void addHeadless();

    public abstract MutableCapabilities getCapabilities();
}
