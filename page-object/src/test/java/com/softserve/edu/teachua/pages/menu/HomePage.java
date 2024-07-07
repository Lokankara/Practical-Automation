package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.pages.user.AddClubModal;
import com.softserve.edu.teachua.pages.user.CitiesDropdownComponent;
import com.softserve.edu.teachua.pages.user.LoginModal;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class HomePage extends TopSearchPart {
    private WebElement dropdownOpenCity;
    private CitiesDropdownComponent dropdownCities;
    public static final String BEGIN_TEACH_LABEL_MESSAGE = "Ініціатива";
    private static final By TRIGGER_CITY = By.cssSelector("div.ant-dropdown-trigger.city");

    private WebElement addClubButton;
    private WebElement teachLabel;

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    public HomePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        addClubButton = driver.findElement(By.cssSelector("button.add-club-button"));
        teachLabel = driver.findElement(By.cssSelector("div.city-name-box h2.city-name"));
        dropdownOpenCity = driver.findElement(TRIGGER_CITY);
    }

    private WebElement getDropdownOpenCity() {return dropdownOpenCity;}

    public CitiesDropdownComponent getDropdownCities() {
        return dropdownCities;
    }

    public WebElement getAddClubButton() {
        return addClubButton;
    }

    public String getAddClubButtonText() {
        return getAddClubButton().getText();
    }

    public void clickAddClubButton() {
        getAddClubButton().click();
    }

    public WebElement getTeachLabel() {
        return teachLabel;
    }

    public String getTeachLabelText() {
        return getTeachLabel().getText();
    }

    // Functional
    // Business Logic

    public LoginModal openLoginModalAddClub() {
        clickAddClubButton();
        return new LoginModal(driver);
    }

    public AddClubModal openAddClubModal() {
        clickAddClubButton();
        return new AddClubModal(driver);
    }

    private CitiesDropdownComponent createDropdownCity() {
        dropdownCities = new CitiesDropdownComponent(driver);
        return getDropdownCities();
    }


    public ClubPage selectCityClubs(Cities city) {
        return openDropdownCity().selectCity(city.getCity());
    }

    protected CitiesDropdownComponent openDropdownCity(){
        clickDropdownCity();
        return createDropdownCity();
    }

    protected void clickDropdownCity() {
        getDropdownOpenCity().click();
    }


}
