package com.softserve.edu.driver;

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
        addArguments(edgeOptions);
        return edgeOptions;
    }
}
