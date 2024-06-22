package com.softserve.edu.teachua.page;

import com.softserve.edu.teachua.modal.AddClubModal;
import com.softserve.edu.teachua.modal.LoginModal;
import com.softserve.edu.teachua.part.TopSearchPart;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends TopSearchPart {

    public static final String BEGIN_TEACH_LABEL_MESSAGE = "Ініціатива";
    public static final String SUCCESS_MESSAGE_TEXT = "Ви успішно залогувалися!";
    public static final String ERROR_MASSAGE = "Введено невірний пароль або email";
    public static final String MESSAGE_SUCCESS_XPATH = "//div[contains(@class, 'ant-message-notice-content')]";

    private WebElement addClubButton;
    private WebElement teachLabel;

    public HomePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        addClubButton = driver.findElement(By.cssSelector("button.add-club-button"));
        teachLabel = driver.findElement(By.cssSelector("div.city-name-box h2.city-name"));
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

    // addClubButton
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
