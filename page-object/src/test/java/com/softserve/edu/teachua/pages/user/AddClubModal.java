package com.softserve.edu.teachua.pages.user;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.top.TopPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.softserve.edu.teachua.data.TestData.BASE_URL;

public class AddClubModal extends TopPart {
    private WebElement headerLink;
    private WebElement closeButton;
    private static final String ADD_CLUB_HEADER_LABEL = "Додати гурток";
    private static final By CLUB_HEADER_LINK = By.cssSelector(".add-club-header");
    private static final By CLOSE_ADD_CLUB_BUTTON = By.xpath("//div[contains(@class, 'modal-add-club')]//button[@aria-label='Close']");

    public AddClubModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected void assertCurrentURL() {
        Assertions.assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    private void initElements() {
        headerLink = waitForVisibilityOfElement(CLUB_HEADER_LINK);
        closeButton = findElement(CLOSE_ADD_CLUB_BUTTON);
    }

    private WebElement getCloseButton() {
        return closeButton;
    }

    private WebElement getHeaderLink() {
        return headerLink;
    }

    private void clickCloseButton() {
        getCloseButton().click();
    }

    public HomePage closeAddClubModal() {
        clickCloseButton();
        return new HomePage(driver);
    }

    public AddClubModal checkHeader() {
        Assertions.assertNotNull(getHeaderLink());
        Assertions.assertTrue(getHeaderLink().isDisplayed());
        Assertions.assertNotNull(getHeaderLink().getText());
        Assertions.assertTrue(getHeaderLink().getText().contains(ADD_CLUB_HEADER_LABEL),
                String.format("Should be %s, but was %s", headerLink.getText(), ADD_CLUB_HEADER_LABEL));
        return this;
    }
}
