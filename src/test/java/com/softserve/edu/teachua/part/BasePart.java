package com.softserve.edu.teachua.part;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePart<T extends BasePart<T>> extends TopPart {

    protected BasePart(WebDriver driver) {
        super(driver);
    }

    protected abstract T self();

    public T checkHeader(String searchText, WebElement header) {
        Assertions.assertNotNull(header);
        Assertions.assertTrue(header.isDisplayed());
        Assertions.assertNotNull(header.getText());
        Assertions.assertTrue(header.getText().contains(searchText), String.format("%s %s", header.getText(), searchText));
        return self();
    }
}
