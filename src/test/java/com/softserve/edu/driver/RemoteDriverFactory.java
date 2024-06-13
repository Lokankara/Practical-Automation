package com.softserve.edu.driver;

import com.softserve.edu.manager.DriverFactoryBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class RemoteDriverFactory extends AbstractDriverFactory {

    private final String browser;
    private final String gridUrl;

    public RemoteDriverFactory(String browser, String gridUrl) {
        this.browser = browser;
        this.gridUrl = gridUrl;
    }

    @Override
    protected WebDriver createDriver() {
        try {
            return new RemoteWebDriver(new URI(gridUrl).toURL(), getCapabilities());
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MutableCapabilities getCapabilities() {
        return DriverFactoryBuilder.getFactory(browser).setHeadless(headless).getCapabilities();
    }
}
