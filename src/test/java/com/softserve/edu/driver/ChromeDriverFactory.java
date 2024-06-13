package com.softserve.edu.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class ChromeDriverFactory extends AbstractDriverFactory {

    @Override
    protected WebDriver createDriver() {
        ChromeDriver driver = new ChromeDriver(getCapabilities());
        driver.executeCdpCommand("Network.enable", Map.of());
        driver.executeCdpCommand("Network.setExtraHTTPHeaders",
                Map.of("headers", Map.of("accept-language", "en-US,en;q=0.9")));
        return driver;
    }

    @Override
    public ChromeOptions getCapabilities() {
        ChromeOptions options = new ChromeOptions();
        addArguments(options);
        return options;
    }
}
