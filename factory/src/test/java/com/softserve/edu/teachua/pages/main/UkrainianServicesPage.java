package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static com.softserve.edu.teachua.data.TestData.SERVICE_URL;

public class UkrainianServicesPage extends BasePage<UkrainianServicesPage> {
    public UkrainianServicesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public UkrainianServicesPage assertCurrentURL() {
        Assertions.assertEquals(SERVICE_URL, getDriver().getCurrentUrl(),
                "Expected URL does not match current URL");
        return this;
    }

    @Override
    public UkrainianServicesPage visit() {
        getDriver().get(SERVICE_URL);
        return this;
    }
}
