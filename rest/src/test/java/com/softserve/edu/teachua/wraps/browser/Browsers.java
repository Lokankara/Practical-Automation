package com.softserve.edu.teachua.wraps.browser;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

interface Browser {
    WebDriver getBrowser();
}

class FirefoxTemporary implements Browser {
    public WebDriver getBrowser() {

        return new FirefoxDriver();
    }
}

class FirefoxWithoutUI implements Browser {
    public WebDriver getBrowser() {

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().setSize(new Dimension(1400, 997));
        return driver;
    }
}

class ChromeTemporary implements Browser {
    public WebDriver getBrowser() {

        return new ChromeDriver();
    }
}

class ChromeIgnoreSSL implements Browser {
    public WebDriver getBrowser() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        return new ChromeDriver(options);
    }
}

class ChromeWithoutUI implements Browser {
    public WebDriver getBrowser() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-proxy-server");
        options.addArguments("--ignore-certificate-errors");
        //options.addArguments("--start-maximized");
        //options.addArguments("--start-fullscreen"); // F11 in browser
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1400, 997));
        return driver;
    }
}

public enum Browsers {
    DEFAULT_TEMPORARY(new ChromeTemporary()),
    FIREFOX_TEMPORARY(new FirefoxTemporary()),
    FIREFOX_WITHOUTUI(new FirefoxWithoutUI()),
    CHROME_TEMPORARY(new ChromeTemporary()),
    CHROME_IGNORESSL(new ChromeIgnoreSSL()),
    CHROME_WITHOUTUI(new ChromeWithoutUI());

    private Browser browser;

    Browsers(Browser browser) {
        this.browser = browser;
    }

    public WebDriver runBrowser() {
        WebDriver driver = browser.getBrowser();
        driver.manage().window().maximize();
        return driver;
    }
}
