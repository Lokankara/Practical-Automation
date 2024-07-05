package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class NewsPage extends TopSearchPart {

    private WebElement cityNewsLabel;

    public NewsPage() {
        ReportUtils.logInfo("Initializing NewsPage");
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in NewsPage");
        cityNewsLabel = search.cssSelector("div.club-sider h2.city-name");
        ReportUtils.logInfo("Initialized cityNewsLabel element");
    }

    public WebElement getCityNewsLabel() {
        ReportUtils.logInfo("Retrieving cityNewsLabel element");
        return cityNewsLabel;
    }

    public String getCityNewsLabelText() {
        String text = getCityNewsLabel().getText();
        ReportUtils.logInfo("Retrieved cityNewsLabel text: " + text);
        return text;
    }
}
