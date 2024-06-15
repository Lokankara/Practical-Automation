package com.softserve.edu.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class ChromeDriverFactory extends AbstractDriverFactory {

    private final ChromeOptions chromeOptions;

    public ChromeDriverFactory() {
        this.chromeOptions = new ChromeOptions();
    }

    public ChromeDriverFactory(ChromeOptions options) {
        this.chromeOptions = options;
    }

    @Override
    protected WebDriver create() {
        addHeadless();
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        addProtocolCommand(driver);
        return driver;
    }

    @Override
    protected void addHeadless() {
        if (headless) {
            this.chromeOptions.addArguments("-headless");
        }
    }

    @Override
    public ChromeOptions getCapabilities() {
        return chromeOptions;
    }

    private void addProtocolCommand(ChromeDriver driver) {
        driver.executeCdpCommand("Network.enable", Map.of());
        driver.executeCdpCommand("Network.setExtraHTTPHeaders",
                Map.of("headers", Map.of("accept-language", "en-US,en;q=0.9")));
    }
}
