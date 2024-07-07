package com.softserve.edu.runner;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.manager.Configuration;

import java.util.List;

public class TestData {
    public static final Browsers BROWSER = Browsers.valueOf(Configuration.getInstance().getBrowser().toUpperCase());
    public static final String PASSWORD = Configuration.getInstance().getPassword();
    public static final String BASE_URL = Configuration.getInstance().getBaseURL();
    public static final String EMAIL = Configuration.getInstance().getEmail();
    public static final String SPEAK_UKRAINIAN_URL = "https://speak-ukrainian.org.ua/";
    public static final String userProfile = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data";

    public static final List<String> testArguments = List.of(
            "--start-maximized",
            "--ignore-certificate-errors",
            "--no-proxy-server",
            "--disable-web-security",
            "--disable-machine-cert-request",
            "--user-data-dir=" + userProfile,
            "--no-sandbox",
            "--headless",
            "--start-fullscreen"
    );

    public static final List<String> commonArguments = List.of(
            "--incognito",
            "--window-size=800,600",
            "--disable-gpu",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-web-security",
            "--allow-running-insecure-content",
            "--ignore-certificate-errors"
    );
}
