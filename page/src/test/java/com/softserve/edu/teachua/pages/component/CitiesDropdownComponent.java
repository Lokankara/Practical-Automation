package com.softserve.edu.teachua.pages.component;

import com.softserve.edu.teachua.exception.DropdownNotFoundException;
import com.softserve.edu.teachua.pages.page.club.ClubPage;
import com.softserve.edu.teachua.pages.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CitiesDropdownComponent extends BasePart<CitiesDropdownComponent> {
    private WebElement city;
    private static final By CITY_LIST = By.xpath("//div[contains(@class, 'ant-dropdown') and not(contains(@class, 'ant-dropdown-hidden')) and contains(@class, 'ant-dropdown-placement-bottom')]//ul[@role='menu']/li[contains(@class, 'ant-dropdown-menu-item-only-child')]");

    public CitiesDropdownComponent(WebDriver driver) {
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
        city = getVisibleElements(CITY_LIST)
                .stream()
                .filter(element -> element.getText().equals(cityName))
                .findFirst()
                .orElseThrow(() -> new DropdownNotFoundException(
                        "City '" + cityName + "' not found in the dropdown."));
    }

    @Override
    protected CitiesDropdownComponent self() {
        return this;
    }
}
