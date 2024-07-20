package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class UkrainianServicePage extends TopSearchPart {

    private WebElement faqTitleLabel;

    public UkrainianServicePage() {
        initElements();
    }

    private void initElements() {
        // init elements
        faqTitleLabel = search.cssSelector("div.faq-title");
    }

    // Page Object

    // faqTitleLabel
    public WebElement getFaqTitleLabel() {
        return faqTitleLabel;
    }

    public String getFaqTitleLabelText() {
        return getFaqTitleLabel().getText();
    }

    // Functional

    // Business Logic

}
