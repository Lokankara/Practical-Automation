package com.softserve.edu.manger;

public class Configuration {

    private static Configuration instance;
    private static final int DEFAULT_IMPLICIT_WAIT = 10;
    private static final String gridURLKey = "URL";
    private static final String browserKey = "BROWSER";
    private static final String passwordKey = "PASSWORD";
    private static final String headlessKey = "HEADLESS";
    private static final String implicitWaitKey = "WAIT";
    private static final String environmentKey = "SELENIUM_URL";

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getBrowser() {
        return System.getenv(browserKey);
    }

    public String getEnvironment() {
        return System.getenv(environmentKey);
    }

    public String getPassword() {
        return System.getenv(passwordKey);
    }

    public int getImplicitWait() {
        String implicitWaitValue = System.getenv(implicitWaitKey);
        return implicitWaitValue != null ? Integer.parseInt(implicitWaitValue) : DEFAULT_IMPLICIT_WAIT;
    }

    public String getGridURL() {
        return System.getenv(gridURLKey) == null ? "" : System.getenv(gridURLKey);
    }

    public boolean isHeadless() {
        return System.getenv(headlessKey) == null || Boolean.parseBoolean(System.getenv(headlessKey));
    }
}
