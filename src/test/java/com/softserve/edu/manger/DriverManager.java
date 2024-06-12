package com.softserve.edu.manger;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class DriverManager {
    protected abstract WebDriver createDriver();
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    protected ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public void quitDriver() {
        if (null != drivers.get()) {
            try {
                drivers.get().quit();
                drivers.remove();
            } catch (Exception e) {
                System.err.printf(e.getMessage());
            }
        }
    }

    public WebDriver getDriver() {
        if (null == drivers.get()) {
            drivers.set(this.createDriver());
        }
        drivers.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        return drivers.get();
    }
}
