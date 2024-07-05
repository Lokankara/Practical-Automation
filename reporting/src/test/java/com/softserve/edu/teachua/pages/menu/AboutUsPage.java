package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class AboutUsPage extends TopSearchPart {

    private WebElement ourContactLabel;

    public AboutUsPage() {
        ReportUtils.logInfo("Initializing AboutUsPage");
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in AboutUsPage");
        ourContactLabel = search.cssSelector("div.social-media span.text");
        ReportUtils.logInfo("Initialized ourContactLabel element");
    }

    public WebElement getOurContactLabel() {
        ReportUtils.logInfo("Retrieving ourContactLabel element");
        return ourContactLabel;
    }

    public String getOurContactLabelText() {
        String text = getOurContactLabel().getText();
        ReportUtils.logInfo("Retrieved ourContactLabel text: " + text);
        return text;
    }
}
