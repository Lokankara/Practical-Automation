package com.softserve.edu.teachua.pages.modal;

import com.softserve.edu.teachua.pages.page.HomePage;
import com.softserve.edu.teachua.pages.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddClubModal extends BasePart<AddClubModal> {
    private WebElement header;
    private WebElement closeButton;
    private static final String ADD_CLUB_HEADER = "Додати гурток";
    private static final By clubHeader = By.cssSelector(".add-club-header");
    private static final By closeAddClub = By.xpath("//div[contains(@class, 'modal-add-club')]//button[@aria-label='Close']");

    public AddClubModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Override
    protected AddClubModal self() {
        return this;
    }

    private void initElements() {
        header = getVisibleElement(clubHeader);
        closeButton = getElement(closeAddClub);
    }

    private WebElement getCloseButton() {
        return closeButton;
    }

    private WebElement getHeader() {
        return header;
    }

    private void clickCloseButton() {
        getCloseButton().click();
    }

    public HomePage closeAddClubModal() {
        clickCloseButton();
        return new HomePage(driver);
    }

    public AddClubModal assertAddClubModalHeader() {
        return checkHeader(ADD_CLUB_HEADER, getHeader());
    }
}
