package com.softserve.edu.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverFactory extends AbstractDriverFactory {

    private final FirefoxOptions firefoxOptions;

    public FirefoxDriverFactory() {
        firefoxOptions = new FirefoxOptions();
    }

    public FirefoxDriverFactory(FirefoxOptions firefoxOptions) {
        this.firefoxOptions = firefoxOptions;
    }

    @Override
    protected WebDriver create() {
        addHeadless();
        return new FirefoxDriver(firefoxOptions);
    }

    @Override
    protected void addHeadless() {
        if (headless) {
            this.firefoxOptions.addArguments("-headless");
        }
    }

    @Override
    public FirefoxOptions getCapabilities() {
        return firefoxOptions;
    }
}
