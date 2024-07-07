package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.BASE_CLUBS_URL;

public class ClubsPage extends BasePage<ClubsPage> {
    public ClubsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public ClubsPage assertCurrentURL() {
        Assertions.assertEquals(BASE_CLUBS_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected BASE_CLUBS_URL");
        return this;
    }

    @Override
    public ClubsPage visit() {
        getDriver().get(BASE_CLUBS_URL);
        return this;
    }
}
