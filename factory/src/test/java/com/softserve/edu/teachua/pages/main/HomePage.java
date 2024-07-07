package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class HomePage extends BasePage<HomePage> {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.city-name-box h2.city-name")
    WebElement teachLabel;

    @Override
    public HomePage assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected BASE_URL");
        return this;
    }

    @Override
    public HomePage visit() {
        getDriver().get(BASE_URL);
        return this;
    }

    public String getTeachLabelText() {
        return teachLabel.getText();
    }
}
