package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClubPage extends BasePage {
    public ClubPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".anticon-caret-down")
    private WebElement dropdownMenu;

    @FindBy(css = "input.ant-select-selection-search-input")
    private WebElement searchInput;

    @FindBy(css = ".anticon-control.advanced-icon")
    private WebElement advancedSearch;

    public ClubPage selectCity(String cityName) {
        dropdownMenu.click();
        WebElement city = driver.findElement(By.xpath("//li/span[contains(text(), '" + cityName + "')]"));

        assertClickable(city).click();
        waitName(city, cityName);
        return new ClubPage(driver);
    }

    public ClubPage assertPresentClubName(String clubName) {
        WebElement element = driver.findElement(By.xpath("//div[contains(@class, 'ant-card-body')]//div[@class='name' and contains(text(), '" + clubName + "')]"));
        assertPresentText(element);
        Assertions.assertEquals(clubName, element.getText());
        return this;
    }

    public ClubSideBar openSideBar() {

        assertClickable(advancedSearch).click();
        return new ClubSideBar(driver);
    }

    private void waitName(WebElement city, String cityName) {
        Assertions.assertEquals(cityName, city.getText());
        wait.until((ExpectedCondition<Boolean>) driver -> cityName.equals(city.getText()));
    }

    public ClubPage gotoPage(int numberPage) {
        WebElement pageLink = driver.findElement(By.xpath("//a[@rel='nofollow' and text()='" + numberPage + "']"));

        assertClickable(pageLink).click();
        return new ClubPage(driver);
    }

    public ClubPage searchClub(String cityName) {
        searchInput.sendKeys(cityName);
        return this;
    }

    public void openModal(String club) {
        WebElement searchClub = driver.findElement(By.xpath("//div[contains(@class,'ant-card')]//div[contains(text(),'" + club + "')]"));
        waitName(searchClub, club);
        searchClub.click();
    }
}
