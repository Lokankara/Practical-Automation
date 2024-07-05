package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class UkrainianServicePage extends TopSearchPart {

    private WebElement faqTitleLabel;

    public UkrainianServicePage() {
        ReportUtils.logInfo("Initializing UkrainianServicePage");
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in UkrainianServicePage");
        faqTitleLabel = search.cssSelector("div.faq-title");
        ReportUtils.logInfo("Initialized faqTitleLabel element");
    }

    public WebElement getFaqTitleLabel() {
        ReportUtils.logInfo("Retrieving faqTitleLabel element");
        return faqTitleLabel;
    }

    public String getFaqTitleLabelText() {
        String text = getFaqTitleLabel().getText();
        ReportUtils.logInfo("Retrieved faqTitleLabel text: " + text);
        return text;
    }
}
