package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class NewsPage extends TopSearchPart {

    private WebElement cityNewsLabel;

    public NewsPage() {
        initElements();
    }

    private void initElements() {
        // init elements
        cityNewsLabel = search.cssSelector("div.club-sider h2.city-name");
    }

    // Page Object

    // cityNewsLabel
    public WebElement getCityNewsLabel() {
        return cityNewsLabel;
    }

    public String getCityNewsLabelText() {
        return getCityNewsLabel().getText();
    }

    // Functional

    // Business Logic

}
