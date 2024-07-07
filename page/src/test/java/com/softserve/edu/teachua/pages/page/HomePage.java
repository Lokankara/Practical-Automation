package com.softserve.edu.teachua.pages.page;

import com.softserve.edu.teachua.pages.modal.AddClubModal;
import com.softserve.edu.teachua.pages.modal.LoginModal;
import com.softserve.edu.teachua.pages.part.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends TopSearchPart {
    private WebElement teachLabel;
    private WebElement addClubButton;
    public static final String BEGIN_TEACH_LABEL_MESSAGE = "Ініціатива";
    public static final String SUCCESS_MESSAGE_TEXT = "Ви успішно залогувалися!";
    public static final String ERROR_MASSAGE = "Введено невірний пароль або email";
    public static final By CLUB_BUTTON = By.cssSelector("button.add-club-button");
    public static final By CITY_NAME = By.cssSelector("div.city-name-box h2.city-name");
    public static final By MESSAGE_SUCCESS_XPATH = By.xpath("//div[contains(@class, 'ant-message-notice-content')]");

    public HomePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        addClubButton = getElement(CLUB_BUTTON);
        teachLabel = getElement(CITY_NAME);
    }

    private WebElement getAddClubButton() {
        return addClubButton;
    }

    private void clickAddClubButton() {
        getAddClubButton().click();
    }

    private WebElement getTeachLabel() {
        return teachLabel;
    }

    public String getAddClubButtonText() {
        return getAddClubButton().getText();
    }

    public String getTeachLabelText() {
        return getTeachLabel().getText();
    }

    public LoginModal openLoginModalAddClub() {
        clickAddClubButton();
        return new LoginModal(driver);
    }

    public HomePage assertSuccessfulLoginMessage() {
        Assertions.assertTrue(getTeachLabelText().contains(HomePage.BEGIN_TEACH_LABEL_MESSAGE));
        return this;
    }

    public AddClubModal openAddClubModal() {
        clickAddClubButton();
        return new AddClubModal(driver);
    }
}
