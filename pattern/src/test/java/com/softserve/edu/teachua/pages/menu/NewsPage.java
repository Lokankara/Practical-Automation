package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewsPage extends TopSearchPart {

    private WebElement cityNewsLabel;

    public NewsPage() {
        super();
        initElements();
    }

    private void initElements() {
        cityNewsLabel = search.cssSelector("div.club-sider h2.city-name");
    }

    public WebElement getCityNewsLabel() {
        return cityNewsLabel;
    }

    public String getCityNewsLabelText() {
        return getCityNewsLabel().getText();
    }

}
