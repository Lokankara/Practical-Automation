package com.softserve.edu.teachua.pages.modal;

import com.softserve.edu.teachua.pages.page.HomePage;
import com.softserve.edu.teachua.pages.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddCenterModal extends BasePart<AddCenterModal> {

    private WebElement header;
    private WebElement closeButton;
    private static final String HEADER = "Додати центр";
    private static final By headerText = By.xpath("//div[@class='modal-title']");
    private static final By closeAddCenter = By.xpath("//div[contains(@class, 'addCenter')]//button[contains(@class, 'ant-modal-close')]");

    public AddCenterModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        header = getElement(headerText);
        closeButton = getElement(closeAddCenter);
    }

    @Override
    protected AddCenterModal self() {
        return this;
    }

    public AddCenterModal assertAddCenterModalHeader() {
        checkHeader(HEADER, header);
        return self();
    }

    public HomePage closeCenterModal() {
        clickCloseAddCenter();
        return new HomePage(driver);
    }

    private WebElement getCloseButton() {
        return closeButton;
    }

    private void clickCloseAddCenter() {
        getCloseButton().click();
    }
}
