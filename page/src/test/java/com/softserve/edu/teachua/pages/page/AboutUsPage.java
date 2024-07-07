package com.softserve.edu.teachua.pages.page;

import com.softserve.edu.teachua.pages.part.TopSearchPart;
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
        ourContactLabel = getElement(By.cssSelector("div.social-media span.text"));
    }

    private WebElement getOurContactLabel() {
        return ourContactLabel;
    }

    public String getOurContactLabelText() {
        return getOurContactLabel().getText();
    }
}
