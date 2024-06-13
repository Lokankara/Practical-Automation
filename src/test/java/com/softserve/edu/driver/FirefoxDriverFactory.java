package com.softserve.edu.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverFactory extends AbstractDriverFactory {

    @Override
    protected WebDriver createDriver() {
        return new FirefoxDriver(getCapabilities());
    }

    @Override
    public FirefoxOptions getCapabilities() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        addArguments(firefoxOptions);
        return firefoxOptions;
    }
}
