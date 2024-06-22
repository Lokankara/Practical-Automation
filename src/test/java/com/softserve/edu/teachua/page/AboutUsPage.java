package com.softserve.edu.teachua.page;

import com.softserve.edu.teachua.part.TopSearchPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AboutUsPage extends TopSearchPart {

    private WebElement ourContactLabel;

    public AboutUsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        ourContactLabel = getElementBy(By.cssSelector("div.social-media span.text"));
    }

    private WebElement getOurContactLabel() {
        return ourContactLabel;
    }

    public String getOurContactLabelText() {
        return getOurContactLabel().getText();
    }

    // Functional
    // Business Logic
}
