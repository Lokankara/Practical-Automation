package com.softserve.edu.util;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private final Dotenv dotenv;
    private static Configuration instance;
    private static final String browserKey = "BROWSER";
    private static final String implicitWaitKey = "WAIT";
    private static final Long DEFAULT_IMPLICIT_WAIT = 3L;
    public static final String DEFAULT_BROWSER = "Firefox";

    public static Configuration getInstance() {
        return instance == null ? new Configuration() : instance;
    }

    private Configuration() {
        dotenv = Dotenv.load();
    }

    public String getBrowser() {
        String browser = System.getenv(browserKey);
        return browser != null ? browser : dotenv.get(browserKey, DEFAULT_BROWSER);
    }

    public Integer getImplicitWait() {
        String implicitWaitValue = System.getenv(implicitWaitKey);
        return implicitWaitValue != null ? Integer.parseInt(implicitWaitValue)
                : Integer.parseInt(dotenv.get(implicitWaitKey, String.valueOf(DEFAULT_IMPLICIT_WAIT)));
    }
}
