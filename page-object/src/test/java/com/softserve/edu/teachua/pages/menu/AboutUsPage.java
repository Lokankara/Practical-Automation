package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class AboutUsPage extends TopSearchPart {

    private WebElement ourContactLabel;

    public AboutUsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        ourContactLabel = driver.findElement(By.cssSelector("div.social-media span.text"));
    }

    public WebElement getOurContactLabel() {
        return ourContactLabel;
    }

    public String getOurContactLabelText() {
        return getOurContactLabel().getText();
    }
}
