package com.softserve.edu.driver;

import com.softserve.edu.manger.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverFactory extends AbstractDriverFactory {

    @Override
    protected WebDriver createDriver() {
        return new EdgeDriver(getCapabilities());
    }

    @Override
    public EdgeOptions getCapabilities() {
        EdgeOptions edgeOptions = new EdgeOptions();
        if (Configuration.getInstance().isHeadless()) {
            edgeOptions.addArguments("--headless");
        }
        edgeOptions.addArguments("--incognito");
        edgeOptions.addArguments("--headless");
        edgeOptions.addArguments("--window-size=1920,1080");
        edgeOptions.addArguments("--disable-gpu");
        edgeOptions.addArguments("--no-sandbox");
        edgeOptions.addArguments("--disable-dev-shm-usage");
        edgeOptions.addArguments("--disable-web-security");
        edgeOptions.addArguments("--allow-running-insecure-content");
        edgeOptions.addArguments("--ignore-certificate-errors");
        return edgeOptions;
    }
}
