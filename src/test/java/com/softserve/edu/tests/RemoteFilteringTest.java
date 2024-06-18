package com.softserve.edu.tests;

import com.softserve.edu.page.Table;
import com.softserve.edu.runner.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

class RemoteFilteringTest extends BaseTest {
    private static final By ACCEPT_COOKIE_XPATH = By.xpath("//footer[contains(@class,'cookie')]//button");
    private static final String BASE_URL = "https://devexpress.github.io/devextreme-reactive/react/grid/docs/guides/filtering/";

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
        closePopup();
    }

    @AfterAll
    public void afterAll() {
        closeDriver();
    }

    @Test
    @DisplayName("Ensure that filtering by a specific letter correctly updates the table rows")
    void checkRemoteFiltering() {
        final String letter = "lon";
        new Table(driver)
                .assertRemoteFilteringPresent()
                .scrollToHeader()
                .switchToIframe()
                .filterCityByLetter(letter)
                .getTableHeader()
                .assertHeader("Country", "City", "Address")
                .assertFirstRow("UK", "London", "Fauntleroy Circus");
    }

    private void closePopup() {
        List<WebElement> footerButton = driver.findElements(ACCEPT_COOKIE_XPATH);
        logger.info(String.format("footerButton.size() = %d%n", footerButton.size()));
        if (!footerButton.isEmpty()) {
            footerButton.get(0).click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }
}
