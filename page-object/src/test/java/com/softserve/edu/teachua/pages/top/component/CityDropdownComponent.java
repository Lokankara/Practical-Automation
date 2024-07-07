package com.softserve.edu.teachua.pages.top.component;

import com.softserve.edu.teachua.exception.DropdownNotFoundException;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.pages.top.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CityDropdownComponent extends Waiter {
    private WebElement city;
    private static final By CITY_LIST = By.xpath("//div[contains(@class, 'ant-dropdown') and not(contains(@class, 'ant-dropdown-hidden')) and contains(@class, 'ant-dropdown-placement-bottom')]//ul[@role='menu']/li[contains(@class, 'ant-dropdown-menu-item-only-child')]");

    public CityDropdownComponent(WebDriver driver) {
        super(driver);
    }

    public ClubPage selectCity(String cityName) {
        getCityElement(cityName);
        clickCity();
        return new ClubPage(driver);
    }

    private void clickCity() {
        city.click();
    }

    private void getCityElement(String cityName) {
        city = waitForVisibilityOfAllElements(CITY_LIST)
                .stream()
                .filter(element -> element.getText().equals(cityName))
                .findFirst()
                .orElseThrow(() -> new DropdownNotFoundException("City '", cityName, "' not found in the dropdown."));
    }
}
