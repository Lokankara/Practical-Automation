package com.softserve.edu.driver;

import com.softserve.edu.manager.Configuration;
import com.softserve.edu.manager.CustomWebDriverListener;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDriverFactory implements DriverFactory {
    private final List<String> commonArguments;
    protected boolean headless = Configuration.getInstance().isHeadless();

    public WebDriver getDriver() {
        return new EventFiringDecorator<>(
                new CustomWebDriverListener())
                .decorate(createDriver());
    }

    protected abstract WebDriver createDriver();

    public abstract MutableCapabilities getCapabilities();

    protected void addArguments(ChromeOptions options) {
        commonArguments.forEach(options::addArguments);
    }

    protected void addArguments(EdgeOptions options) {
        commonArguments.forEach(options::addArguments);
    }

    protected void addArguments(FirefoxOptions options) {
        options.addPreference("browser.privatebrowsing.autostart", true);
        options.addPreference("dom.disable_open_during_load", false);
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.volume_scale", "0.0");
        options.addPreference("toolkit.startup.max_resumed_crashes", "-1");
        options.addPreference("browser.startup.homepage", "about:blank");
        options.addPreference("browser.startup.page", 1);
        options.addPreference("browser.startup.homepage_override.mstone", "ignore");
        options.addPreference("width", 800);
        options.addPreference("height", 600);
    }

    public DriverFactory setHeadless(boolean isHeadless) {
        this.headless = isHeadless;
        if (this.headless) {
            commonArguments.add("--headless");
        }
        return this;
    }

    public AbstractDriverFactory() {
        commonArguments = new ArrayList<>(List.of(
                "--incognito",
                "--window-size=800,600",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-web-security",
                "--allow-running-insecure-content",
                "--ignore-certificate-errors"
        ));
    }
}
