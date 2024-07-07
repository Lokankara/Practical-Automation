package com.softserve.edu.teachua.pages.page;

import com.softserve.edu.teachua.pages.part.TopSearchPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UkrainianServicePage extends TopSearchPart {

    private WebElement faqTitleLabel;
    private static final By FAQ_TITLE = By.cssSelector("div.faq-title");

    public UkrainianServicePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        faqTitleLabel = getElement(FAQ_TITLE);
    }

    public WebElement getFaqTitleLabel() {
        return faqTitleLabel;
    }

    public String getFaqTitleLabelText() {
        return getFaqTitleLabel().getText();
    }
}
