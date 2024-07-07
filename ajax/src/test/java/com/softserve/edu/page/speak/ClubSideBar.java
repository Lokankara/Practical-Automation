package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ClubSideBar extends BasePage {
    protected ClubSideBar(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".club-list-label")
    private WebElement header;

    @FindBy(css = "span.ant-select-selection-item")
    private WebElement selection;

    public ClubPage assertHeader() {
        assertPresentText(header);
        Assertions.assertEquals("Розширений пошук", header.getText());

        return new ClubPage(getDriver());
    }

    public ClubSideBar chooseCity(String cityName) {
        selection.click();
        String city = "//div[@class='ant-select-item ant-select-item-option'][@title='" + cityName + "']";
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(city))).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));

        return this;
    }
}
