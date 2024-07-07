package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.pages.top.component.PaginationComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdvancedClubPage extends ClubPage {
    private WebElement searchSelection;
    private WebElement submitButton;
    private static final By SELECTION_ITEM = By.cssSelector("span.ant-select-selection-item");
    private static final By SUBMIT_BUTTON = By.xpath("//div[@class='mobile-use-button']/button");
    private static final String ITEM_OPTION = "//div[@class='ant-select-item ant-select-item-option'][@title='%s']";

    public AdvancedClubPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        submitButton = driver.findElement(SUBMIT_BUTTON);
        searchSelection = driver.findElement(SELECTION_ITEM);
        pagination = new PaginationComponent(driver);
        clubsContainer = new ClubsContainer(waitUntilExpectedSize(CLUBS_COMPONENT, 6)
                .stream()
                .map(element -> new ClubComponent(driver, element))
                .toList());
    }

    private WebElement getSubmitButton() {
        return submitButton;
    }

    private WebElement getSearchSelection() {
        return searchSelection;
    }

    private void clickSubmitButton() {
        getSubmitButton().click();
    }


    @Override
    public AdvancedClubPage chooseCity(Cities city) {
        waitForPresenceOfElementLocated(By.xpath(String.format(ITEM_OPTION, city.getCity()))).click();
        return this;
    }
}
