package com.softserve.edu.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverFactory extends AbstractDriverFactory {

    private final EdgeOptions edgeOptions;

    public EdgeDriverFactory() {
        this.edgeOptions = new EdgeOptions();
    }

    public EdgeDriverFactory(EdgeOptions edgeOptions) {
        this.edgeOptions = edgeOptions;
    }

    @Override
    protected WebDriver create() {
        addHeadless();
        return new EdgeDriver(edgeOptions);
    }

    @Override
    protected void addHeadless() {
        if (headless) {
            this.edgeOptions.addArguments("-headless");
        }
    }

    @Override
    public EdgeOptions getCapabilities() {
        return edgeOptions;
    }
}
