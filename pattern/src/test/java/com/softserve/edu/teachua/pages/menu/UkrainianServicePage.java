package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class UkrainianServicePage extends TopSearchPart {

    private WebElement faqTitleLabel;

    public UkrainianServicePage() {
        super();
        initElements();
    }

    private void initElements() {
        faqTitleLabel = search.cssSelector("div.faq-title");
    }

    public WebElement getFaqTitleLabel() {
        return faqTitleLabel;
    }

    public String getFaqTitleLabelText() {
        return getFaqTitleLabel().getText();
    }
}
