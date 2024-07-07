package com.softserve.edu.teachua.pages.component.form;

import com.softserve.edu.teachua.pages.top.BaseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputComponent extends BaseModel {
    public InputComponent(WebDriver driver, WebElement element) {
        super(driver);
        this.label = getAttribute(element, "aria-label");
        this.name = getAttribute(element, "name");
        this.text = getAttribute(element, "value");
    }

    private final String label;
    private final String name;
    private final String text;

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    private String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }
}
