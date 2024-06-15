package com.softserve.edu.manager;

import io.github.cdimascio.dotenv.Dotenv;

import java.time.Duration;

public class Configuration {
    private final Dotenv dotenv;
    private static Configuration instance;
    private static final String CIKey = "CI";
    private static final String baseURLKey = "URL";
    private static final String browserKey = "BROWSER";
    private static final String passwordKey = "PASSWORD";
    private static final String implicitWaitKey = "WAIT";
    private static final String headlessKey = "IS_HEADLESS";
    private static final Long DEFAULT_IMPLICIT_WAIT = 5L;
    private static final String DEFAULT_BROWSER = "Firefox";
    private static final String DEFAULT_URL = "https://www.google.com/";

    public static Configuration getInstance() {
        return instance == null ? new Configuration() : instance;
    }

    private Configuration() {
        dotenv = Dotenv.load();
    }

    public String getPassword() {
        String password = System.getenv(passwordKey);
        return password != null ? password : dotenv.get(passwordKey);
    }

    public String getBrowser() {
        String browser = System.getenv(browserKey);
        return browser != null ? browser : dotenv.get(browserKey, DEFAULT_BROWSER);
    }

    public String getDefaultUrl() {
        return DEFAULT_URL;
    }

    public String getBaseURL() {
        String url = System.getenv(baseURLKey);
        return url != null ? url : dotenv.get(baseURLKey, DEFAULT_URL);
    }

    public boolean isHeadless() {
        return isServerRun()
                || "true".equalsIgnoreCase(System.getenv(headlessKey))
                || "true".equalsIgnoreCase(dotenv.get(headlessKey));
    }

    public Duration getDuration() {
        return Duration.ofSeconds(getImplicitWaitDuration());
    }

    private long getImplicitWaitDuration() {
        String implicitWaitValue = System.getenv(implicitWaitKey);
        return implicitWaitValue != null ? Long.parseLong(implicitWaitValue)
                : Long.parseLong(dotenv.get(implicitWaitKey, String.valueOf(DEFAULT_IMPLICIT_WAIT)));
    }

    public static boolean isServerRun() {
        String ci = System.getenv(CIKey);
        return ci != null;
    }
}
