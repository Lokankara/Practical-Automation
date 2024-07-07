package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.exception.PaginationException;
import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.pages.top.component.PaginationComponent;
import com.softserve.edu.teachua.pages.user.CitiesDropdownComponent;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_CLUBS_URL;

public class ClubPage extends TopSearchPart {
    protected WebElement dropdownOpenCity;
    protected ClubsContainer clubsContainer;
    protected PaginationComponent pagination;
    protected CitiesDropdownComponent dropdownCities;
    private static final By TRIGGER_CITY = By.cssSelector("div.ant-dropdown-trigger.city");
    protected static final By CLUBS_COMPONENT = By.cssSelector("div.ant-card.ant-card-bordered");

    public ClubPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_CLUBS_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        pagination = new PaginationComponent(driver);
        clubsContainer = new ClubsContainer(driver.findElements(CLUBS_COMPONENT)
                .stream()
                .map(element -> new ClubComponent(driver, element))
                .toList());
    }

    private ClubsContainer getClubContainer() {
        return clubsContainer;
    }

    public ClubComponent getFirstClubComponent() {
        return getClubContainer().getClubComponents().get(0);
    }

    protected PaginationComponent getPagination() {
        return pagination;
    }

    private WebElement getDropdownOpenCity() {
        createDropdownOpenCity();
        return dropdownOpenCity;
    }

    private void createDropdownOpenCity() {
        dropdownOpenCity = findElement(TRIGGER_CITY);
    }

    private CitiesDropdownComponent getDropdownCities() {
        return dropdownCities;
    }

    private void clickDropdownCity() {
        getDropdownOpenCity().click();
    }

    private CitiesDropdownComponent createDropdownCity() {
        dropdownCities = new CitiesDropdownComponent(driver);
        return getDropdownCities();
    }

    protected CitiesDropdownComponent openDropdownCity() {
        clickDropdownCity();
        return createDropdownCity();
    }

    public ClubPage chooseCity(Cities city) {
        return openDropdownCity()
                .selectCity(city.getCity());
    }

    public ClubPage assertCityHeader(Cities city) {
        boolean isPresent = findElement(By.cssSelector(".city-name")).getText().contains(city.getCity());
        Assertions.assertTrue(isPresent);
        return this;
    }

    public ClubPage gotoPreviousClubPage() {
        if (!getPagination().isEnablePreviousPageLink()) {
            throw new PaginationException("The previous page is not available");
        }
        getPagination().clickPreviousPageLink();
        return new ClubPage(driver);
    }

    public ClubPage gotoNextClubPage() {
        if (!getPagination().isEnableNextPageLink()) {
            throw new PaginationException("The next page is not available");
        }
        getPagination().clickNextPageLink();
        return new ClubPage(driver);
    }

    public ClubInfoModal openClubInfoModal(String searchText) {
        return getClubContainer()
                .getClubComponentByTitle(searchText)
                .openClubInfoModal();
    }

    public ClubPage gotoPage(int numberPage) {
        getPagination().clickPageLinkByNumber(numberPage);
        return new ClubPage(driver);
    }

    public void untilClubComponentIsFoundByPartialTitle(String club) {
        while (!isExistClubComponentByPartialTitle(club) || getPagination().isEnableNextPageLink()) {
            getPagination().clickNextPageLink();
        }
    }

    public boolean isExistClubComponentByPartialTitle(String partialTitle) {
        return getClubContainer().getClubComponentTitles()
                .stream()
                .anyMatch(current -> current.toLowerCase().contains(partialTitle.toLowerCase()));
    }

    public ClubComponent getClubComponentByPartialTitle(String club) {
        return clubsContainer.getClubComponentByPartialTitle(club);
    }

    public boolean isEnableNextPageLink() {
        return getPagination().isEnableNextPageLink();
    }

    public boolean isEnablePreviousPageLink() {
        return getPagination().isEnablePreviousPageLink();
    }
}
