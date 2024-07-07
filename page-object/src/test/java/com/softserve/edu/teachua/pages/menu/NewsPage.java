package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class NewsPage extends TopSearchPart {

    private WebElement cityNewsLabel;

    public NewsPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        cityNewsLabel = driver.findElement(By.cssSelector("div.club-sider h2.city-name"));
    }

    public WebElement getCityNewsLabel() {
        return cityNewsLabel;
    }

    public String getCityNewsLabelText() {
        return getCityNewsLabel().getText();
    }

    // Functional

    // Business Logic

}
