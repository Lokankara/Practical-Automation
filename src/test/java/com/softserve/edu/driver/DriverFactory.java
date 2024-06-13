package com.softserve.edu.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public interface DriverFactory {

    WebDriver getDriver();

    MutableCapabilities getCapabilities();

    DriverFactory setHeadless(boolean isHeadless);
}
