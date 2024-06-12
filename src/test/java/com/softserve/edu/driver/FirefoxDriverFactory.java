package com.softserve.edu.driver;

import com.softserve.edu.manger.Configuration;
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
        if (Configuration.getInstance().isHeadless()) {
            firefoxOptions.addArguments("--headless");
        }
        firefoxOptions.addArguments("--incognito");
        firefoxOptions.addArguments("--window-size=1920,1080");
        firefoxOptions.addArguments("--disable-gpu");
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--disable-dev-shm-usage");
        firefoxOptions.addArguments("--disable-web-security");
        firefoxOptions.addArguments("--allow-running-insecure-content");
        firefoxOptions.addArguments("--ignore-certificate-errors");
        return firefoxOptions;
    }
}
