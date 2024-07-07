package com.softserve.edu.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface Waitable<T> {
    T apply(WebDriver driver, By locator);
}
