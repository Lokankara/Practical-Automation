package com.softserve.edu.page.react;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Row extends BasePage {
    protected Row(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "tbody > tr:first-child td:first-child")
    private WebElement countryCell;

    @FindBy(css = "tbody > tr:first-child td:nth-child(2)")
    private WebElement cityCell;

    @FindBy(css = "tbody > tr:first-child > td:last-child")
    private WebElement addressCell;

    @FindBy(css = "tr:first-child th:first-child")
    private WebElement countryHeader;

    @FindBy(css = "tr:first-child th:nth-child(2)")
    private WebElement cityHeader;

    @FindBy(css = "tr:first-child > th:nth-child(3)")
    private WebElement addressHeader;

    public void assertFirstRow(String country, String city, String address) {
        Assertions.assertTrue(country.contains(countryCell.getText()), "Not found in the Country column.");
        Assertions.assertTrue(address.contains(addressCell.getText()), "Not found in the Address column.");
        Assertions.assertTrue(city.contains(cityCell.getText()), "Not found in the City column.");
    }

    public Row assertHeader(String country, String city, String address) {
        Assertions.assertEquals(country, countryHeader.getText(), "Not found in the Country header");
        Assertions.assertEquals(address, addressHeader.getText(), "Not found in the Address header");
        Assertions.assertEquals(city, cityHeader.getText(), "Not found in the City header");

        return this;
    }
}
