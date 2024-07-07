package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;

public class ClubPage extends BasePage {

    public ClubPage(WebDriver driver) {super(driver);}

    @FindBy(css = ".anticon-caret-down")
    private WebElement dropdownMenu;

    @FindBy(css = "input.ant-select-selection-search-input")
    private WebElement searchInput;

    @FindBy(css = ".anticon-control.advanced-icon")
    private WebElement advancedSearch;

    public ClubPage selectCity(String cityName) {
        dropdownMenu.click();
        WebElement city = getElementByXpath("//li/span[contains(text(), '" + cityName + "')]");
        assertClickable(city).click();
        waitName(city, cityName);

        return new ClubPage(getDriver());
    }

    public ClubPage assertPresentClubName(String clubName) {
        String cardClub = "//div[contains(@class, 'ant-card-body')]//div[@class='name' and contains(text(), '" + clubName + "')]";
        WebElement element = getElementByXpath(cardClub);
        assertPresentText(element);
        Assertions.assertEquals(clubName, element.getText());

        return this;
    }

    public ClubSideBar openSideBar() {
        assertClickable(advancedSearch).click();

        return new ClubSideBar(getDriver());
    }

    private void waitName(WebElement city, String cityName) {
        Assertions.assertEquals(cityName, city.getText());
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        getWait().until((ExpectedCondition<Boolean>) driver -> cityName.equals(city.getText()));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }

    public ClubPage gotoPage(int numberPage) {
        WebElement pageLink = getElementByXpath("//a[@rel='nofollow' and text()='" + numberPage + "']");
        assertClickable(pageLink).click();

        return new ClubPage(getDriver());
    }

    public ClubPage searchClub(String cityName) {
        searchInput.sendKeys(cityName);

        return this;
    }

    public void openModal(String club) {
        String cardClub = "//div[contains(@class,'ant-card')]//div[contains(text(),'" + club + "')]";
        WebElement searchClub = getElementByXpath(cardClub);
        waitName(searchClub, club);

        searchClub.click();
    }
}
