package com.softserve.edu.teachua.wraps.browser;

import com.softserve.edu.teachua.tools.ReportUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}

class FirefoxWithoutUI implements Browser {
    public WebDriver getBrowser() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        return new FirefoxDriver(options);
    }
}

class ChromeTemporary implements Browser {
    public WebDriver getBrowser() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}

class ChromeIgnoreSSL implements Browser {
    public WebDriver getBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        return new ChromeDriver(options);
    }
}

class ChromeWithoutUI implements Browser {
    public WebDriver getBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-proxy-server");
        options.addArguments("--ignore-certificate-errors");
        return new ChromeDriver(options);
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

    private Browsers(Browser browser) {
        this.browser = browser;
    }

    public WebDriver runBrowser() {
        WebDriver driver = browser.getBrowser();
        driver.manage().window().maximize();
        ReportUtils.logBrowserDetails(driver);
        return driver;
    }
}
