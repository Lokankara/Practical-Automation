package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

        return new ClubPage(driver);
    }

    public ClubSideBar chooseCity(String cityName) {
        selection.click();
        String city = "//div[@class='ant-select-item ant-select-item-option'][@title='" + cityName + "']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(city))).click();
        return this;
    }
}
