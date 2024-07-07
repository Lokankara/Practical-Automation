package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class UkrainianServicePage extends TopSearchPart {

    private WebElement faqTitleLabel;

    public UkrainianServicePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        faqTitleLabel = driver.findElement(By.cssSelector("div.faq-title"));
    }

    public WebElement getFaqTitleLabel() {
        return faqTitleLabel;
    }

    public String getFaqTitleLabelText() {
        return getFaqTitleLabel().getText();
    }
}
