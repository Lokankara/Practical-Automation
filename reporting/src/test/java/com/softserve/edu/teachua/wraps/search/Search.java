package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.tools.ReportUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class Search {
    private static final String NO_SUCH_ELEMENT = "Unable to locate element(s): %s";

    protected abstract WebElement getWebElement(By by);

    protected abstract WebElement getWebElement(By by, WebElement fromWebElement);

    protected abstract List<WebElement> getWebElements(By by);

    protected abstract List<WebElement> getWebElements(By by, WebElement fromWebElement);

    @Step("Search WebElement: {by}")
    private WebElement searchWebElement(By by) {
        try {
            WebElement element = getWebElement(by);
            ReportUtils.logAction(String.format("Found WebElement by: %s", by.toString()));
            return element;
        } catch (Exception e) {
            ReportUtils.logError(String.format(NO_SUCH_ELEMENT, by.toString()));
            throw new RuntimeException(String.format(NO_SUCH_ELEMENT, by));
        }
    }

    @Step("Search WebElement: {by} within {fromWebElement}")
    private WebElement searchWebElement(By by, WebElement fromWebElement) {
        try {
            WebElement element = getWebElement(by, fromWebElement);
            ReportUtils.logAction(String.format("Found WebElement by: %s within %s", by.toString(), fromWebElement.toString()));
            return element;
        } catch (Exception e) {
            ReportUtils.logError(String.format(NO_SUCH_ELEMENT, by.toString()));
            throw new RuntimeException(String.format(NO_SUCH_ELEMENT, by));
        }
    }

    @Step("Search WebElements: {by}")
    private List<WebElement> searchWebElements(By by) {
        try {
            List<WebElement> elements = getWebElements(by);
            ReportUtils.logAction(String.format("Found WebElements by: %s", by.toString()));
            return elements;
        } catch (Exception e) {
            ReportUtils.logError(String.format(NO_SUCH_ELEMENT, by.toString()));
            throw new RuntimeException(String.format(NO_SUCH_ELEMENT, by));
        }
    }

    @Step("Search WebElements: {by} within {fromWebElement}")
    private List<WebElement> searchWebElements(By by, WebElement fromWebElement) {
        try {
            List<WebElement> elements = getWebElements(by, fromWebElement);
            ReportUtils.logAction(String.format("Found WebElements by: %s within %s", by.toString(), fromWebElement.toString()));
            return elements;
        } catch (Exception e) {
            ReportUtils.logError(String.format(NO_SUCH_ELEMENT, by.toString()));
            throw new RuntimeException(String.format(NO_SUCH_ELEMENT, by));
        }
    }

    public List<WebElement> webElements(By by) {
        return searchWebElements(by);
    }

    public boolean isLocated(By by) {
        return !searchWebElements(by).isEmpty();
    }

    public boolean isLocatedCss(String cssSelector) {
        return !cssSelectors(cssSelector).isEmpty();
    }

    public boolean isLocated(By by, String text) {
        List<WebElement> elements = searchWebElements(by);
        return (!elements.isEmpty()) && (elements.get(0).getText().contains(text));
    }

    public boolean isLocatedCss(String cssSelector, String text) {
        List<WebElement> elements = cssSelectors(cssSelector);
        return (!elements.isEmpty()) && (elements.get(0).getText().contains(text));
    }

    public WebElement id(String id) {
        return searchWebElement(By.id(id));
    }

    public WebElement name(String name) {
        return searchWebElement(By.name(name));
    }

    public WebElement xpath(String xpath) {
        return searchWebElement(By.xpath(xpath));
    }

    public WebElement cssSelector(String cssSelector) {
        return searchWebElement(By.cssSelector(cssSelector));
    }

    public WebElement className(String className) {
        return searchWebElement(By.className(className));
    }

    public WebElement partialLinkText(String partialLinkText) {
        return searchWebElement(By.partialLinkText(partialLinkText));
    }

    public WebElement linkText(String linkText) {
        return searchWebElement(By.linkText(linkText));
    }

    public WebElement tagName(String tagName) {
        return searchWebElement(By.tagName(tagName));
    }

    public WebElement id(String id, WebElement fromWebElement) {
        return searchWebElement(By.id(id), fromWebElement);
    }

    public WebElement name(String name, WebElement fromWebElement) {
        return searchWebElement(By.name(name), fromWebElement);
    }

    public WebElement xpath(String xpath, WebElement fromWebElement) {
        return searchWebElement(By.xpath(xpath), fromWebElement);
    }

    public WebElement cssSelector(String cssSelector, WebElement fromWebElement) {
        return searchWebElement(By.cssSelector(cssSelector), fromWebElement);
    }

    public WebElement className(String className, WebElement fromWebElement) {
        return searchWebElement(By.className(className), fromWebElement);
    }

    public WebElement partialLinkText(String partialLinkText, WebElement fromWebElement) {
        return searchWebElement(By.partialLinkText(partialLinkText), fromWebElement);
    }

    public WebElement linkText(String linkText, WebElement fromWebElement) {
        return searchWebElement(By.linkText(linkText), fromWebElement);
    }

    public WebElement tagName(String tagName, WebElement fromWebElement) {
        return searchWebElement(By.tagName(tagName), fromWebElement);
    }

    public List<WebElement> names(String name) {
        return searchWebElements(By.name(name));
    }

    public List<WebElement> names(String name, WebElement fromWebElement) {
        return searchWebElements(By.name(name), fromWebElement);
    }

    public List<WebElement> xpaths(String xpath) {
        return searchWebElements(By.xpath(xpath));
    }

    public List<WebElement> xpaths(String xpath, WebElement fromWebElement) {
        return searchWebElements(By.xpath(xpath), fromWebElement);
    }

    public List<WebElement> cssSelectors(String cssSelector) {
        return searchWebElements(By.cssSelector(cssSelector));
    }

    public List<WebElement> cssSelectors(String cssSelector, WebElement fromWebElement) {
        return searchWebElements(By.cssSelector(cssSelector), fromWebElement);
    }

    public List<WebElement> classNames(String className) {
        return searchWebElements(By.className(className));
    }

    public List<WebElement> classNames(String className, WebElement fromWebElement) {
        return searchWebElements(By.className(className), fromWebElement);
    }

    public List<WebElement> partialLinkTexts(String partialLinkText) {
        return searchWebElements(By.partialLinkText(partialLinkText));
    }

    public List<WebElement> partialLinkTexts(String partialLinkText, WebElement fromWebElement) {
        return searchWebElements(By.partialLinkText(partialLinkText), fromWebElement);
    }

    public List<WebElement> linkTexts(String linkText) {
        return searchWebElements(By.linkText(linkText));
    }

    public List<WebElement> linkTexts(String linkText, WebElement fromWebElement) {
        return searchWebElements(By.linkText(linkText), fromWebElement);
    }

    public List<WebElement> tagNames(String tagName) {
        return searchWebElements(By.tagName(tagName));
    }

    public List<WebElement> tagNames(String tagName, WebElement fromWebElement) {
        return searchWebElements(By.tagName(tagName), fromWebElement);
    }
}
