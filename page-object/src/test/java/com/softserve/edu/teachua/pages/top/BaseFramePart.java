package com.softserve.edu.teachua.pages.top;

import org.openqa.selenium.WebDriver;

public abstract class BaseFramePart extends Waiter {

    protected WebDriver driver;

    public BaseFramePart(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
