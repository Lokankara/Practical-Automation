package com.softserve.edu.teachua.pages.page;

import com.softserve.edu.teachua.pages.part.TopSearchPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewsPage extends TopSearchPart {

    private WebElement cityNewsLabel;
    private static final By CITY_NAME = By.cssSelector("div.club-sider h2.city-name");

    public NewsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        cityNewsLabel = getElement(CITY_NAME);
    }

    public WebElement getCityNewsLabel() {
        return cityNewsLabel;
    }

    public String getCityNewsLabelText() {
        return getCityNewsLabel().getText();
    }
}
