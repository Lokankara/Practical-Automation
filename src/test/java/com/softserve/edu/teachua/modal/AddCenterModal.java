package com.softserve.edu.teachua.modal;

import com.softserve.edu.teachua.page.HomePage;
import com.softserve.edu.teachua.part.BasePart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddCenterModal extends BasePart<AddCenterModal> {

    private WebElement header;
    private WebElement closeButton;
    private static final String HEADER = "Додати центр";
    private final By headerText = By.xpath("//div[@class='modal-title']");
    private final By closeAddCenter = By.xpath("//div[contains(@class, 'addCenter')]//button[contains(@class, 'ant-modal-close')]");

    public AddCenterModal(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        header = getElementBy(headerText);
        closeButton = getElementBy(closeAddCenter);
    }

    @Override
    protected AddCenterModal self() {
        return this;
    }

    public AddCenterModal checkAddCenterModalHeader() {
        checkHeader(HEADER, header);
        return self();
    }

    public HomePage closeCenterModal() {
        clickCloseAddCenter();
        return new HomePage(driver);
    }

    private void clickCloseAddCenter() {
        closeButton.click();
    }
}
