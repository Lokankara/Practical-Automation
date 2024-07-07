package com.softserve.edu.teachua.pages.top;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Interactions<T> {

    T waitForElementToBeClickable(By locator);

    T waitForPresenceOfElementLocated(By locator);

    T waitForVisibilityOfElement(By locator);

    T waitForVisibilityOfElement(T element);

    List<T> waitForVisibilityOfAllElements(By locator);

    List<T> waitForVisibilityOfAllElements(List<T> elements);

    List<T> waitForPresenceOfAllElements(By locator);

    Boolean waitForTextToBePresentInElementLocated(T element, String text);

    Boolean waitForTextToBePresentInElementLocated(By locator, String text);

    Boolean waitForInvisibilityOfElement(T element);

    List<T> waitMoreThanExpectedSize(By locator, int size);

    List<T> waitUntilExpectedSize(By locator, int expectedSize);

    Boolean waitForInvisibilityOfElement(By locator);

    Boolean waitForCurrentUrl(String url);

    void waitStalenessOf(WebElement element);
}
