package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.ABOUT_US_URL;

public class AboutUsPage extends BasePage<AboutUsPage> {

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public AboutUsPage assertCurrentURL() {
        Assertions.assertEquals(ABOUT_US_URL, getDriver().getCurrentUrl(),
                "Current URL should match expected HELP_US_URL");
        return this;
    }

    @Override
    public AboutUsPage visit() {
        getDriver().get(ABOUT_US_URL);
        return this;
    }
}
