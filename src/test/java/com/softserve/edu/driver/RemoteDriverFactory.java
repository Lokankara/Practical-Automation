package com.softserve.edu.driver;

import com.softserve.edu.reporter.LoggerUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.softserve.edu.runner.TestData.commonArguments;

public class RemoteDriverFactory extends AbstractDriverFactory {
    private final String browser;
    private final String gridUrl;

    private final DesiredCapabilities capabilities;

    public RemoteDriverFactory(String browser, String gridUrl) {
        this.gridUrl = gridUrl;
        this.browser = browser.toUpperCase();
        capabilities = new DesiredCapabilities();
    }

    @Override
    protected WebDriver create() {
        addArguments();
        try {
            return new RemoteWebDriver(new URI(gridUrl).toURL(), getCapabilities());
        } catch (URISyntaxException | MalformedURLException e) {
            LoggerUtils.logError(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void addHeadless() {
        if (headless) {
            LoggerUtils.logError("-headless", String.valueOf(capabilities));
        }
    }

    @Override
    public MutableCapabilities getCapabilities() {
        return capabilities;
    }

    private void addArguments() {
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(commonArguments);
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments(commonArguments);
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        } else if ("edge".equalsIgnoreCase(browser)) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments(commonArguments);
            capabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
        } else {
            LoggerUtils.logError("Browser", browser, "not supported.");
        }
    }
}
