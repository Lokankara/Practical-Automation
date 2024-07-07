package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.softserve.edu.teachua.data.TestData.NEWS_HEADER;
import static com.softserve.edu.teachua.data.TestData.NEWS_URL;

public class NewsPage extends BasePage<NewsPage> {
    public NewsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@class, 'city-name-box')]/h2[@class='city-name']")
    private WebElement headerLabel;

    @Override
    public NewsPage assertCurrentURL() {
        Assertions.assertEquals(NEWS_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected NEWS_URL");
        return this;
    }

    @Override
    public NewsPage visit() {
        getDriver().get(NEWS_URL);
        return this;
    }

    public NewsPage assertHeader() {
        Assertions.assertTrue(headerLabel.isDisplayed());
        Assertions.assertEquals(NEWS_HEADER, getHeaderText());
        return this;
    }

    public String getHeaderText() {
        return headerLabel.getText();
    }
}
