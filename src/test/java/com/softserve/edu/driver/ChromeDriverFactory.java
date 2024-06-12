package com.softserve.edu.driver;

import com.softserve.edu.manger.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverFactory extends AbstractDriverFactory {

    @Override
    protected WebDriver createDriver() {
        return new ChromeDriver(getCapabilities());
    }

    @Override
    public ChromeOptions getCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (Configuration.getInstance().isHeadless()) {
            chromeOptions.addArguments("--headless");
        }
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--ignore-certificate-errors");
        return chromeOptions;
    }
}
