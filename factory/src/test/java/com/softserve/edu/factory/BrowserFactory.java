package com.softserve.edu.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public enum BrowserFactory {
    CHROME {
        @Override
        public WebDriver createDriver() {
            return new ChromeDriver();
        }
    },
    EDGE {
        @Override
        public WebDriver createDriver() {
            return new EdgeDriver();
        }
    },
    SAFARI {
        @Override
        public WebDriver createDriver() {
            return new SafariDriver();
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver() {
            return new FirefoxDriver();
        }
    },
    DEFAULT {
        @Override
        public WebDriver createDriver() {return new FirefoxDriver();}
    };

    public abstract WebDriver createDriver();
}
