package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class AboutUsPage extends TopSearchPart {

    private WebElement ourContactLabel;

    public AboutUsPage() {
        super();
        initElements();
    }

    private void initElements() {
        ourContactLabel = search.cssSelector("div.social-media span.text");
    }

    public WebElement getOurContactLabel() {
        return ourContactLabel;
    }

    public String getOurContactLabelText() {
        return getOurContactLabel().getText();
    }

}
