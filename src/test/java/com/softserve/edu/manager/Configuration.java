package com.softserve.edu.manager;

import java.time.Duration;

public class Configuration {
    private static Configuration instance;
    private static final String baseURLKey = "URL";
    private static final String browserKey = "BROWSER";
    private static final String implicitWaitKey = "WAIT";
    private static final String DEFAULT_BROWSER = "Firefox";
    private static final String headlessKey = "IS_HEADLESS";
    private static final String DEFAULT_URL = "https://www.google.com";
    private static final Long DEFAULT_IMPLICIT_WAIT = 5L;
    private static final String passwordKey = "PASSWORD";
    public static final String PASSWORD = "Qwerty_1";

    public static Configuration getInstance() {
        return instance == null ? new Configuration() : instance;
    }

    public String getPassword() {
        String password = System.getenv(passwordKey);
        return password != null ? password : PASSWORD;
    }

    public String getBrowser() {
        String browser = System.getenv(browserKey);
        return browser == null ? DEFAULT_BROWSER : browser;
    }

    public String getBaseURL() {
        return System.getenv(baseURLKey) == null
                ? DEFAULT_URL : System.getenv(baseURLKey);
    }

    public boolean isHeadless() {
        return !isServerRun() && System.getenv(headlessKey) == null
                || Boolean.parseBoolean(System.getenv(headlessKey));
    }

    public Duration getDuration() {
        return Duration.ofSeconds(getImplicitWaitDuration());
    }

    private Long getImplicitWaitDuration() {
        String implicitWaitValue = System.getenv(implicitWaitKey);
        return implicitWaitValue == null
                ? DEFAULT_IMPLICIT_WAIT
                : Integer.parseInt(implicitWaitValue);
    }

    public static boolean isServerRun() {
        String ci = System.getenv("CI");
        return ci != null;
    }
}
