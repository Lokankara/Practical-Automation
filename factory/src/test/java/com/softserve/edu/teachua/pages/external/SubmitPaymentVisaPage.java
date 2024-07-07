package com.softserve.edu.teachua.pages.external;

import com.softserve.edu.teachua.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubmitPaymentVisaPage extends PaymentPage {

    @FindBy(css = ".shell-container .screen .content-main.welcome-page h1")
    private WebElement paymentContainer;

    public SubmitPaymentVisaPage(WebDriver driver) {
        super(driver);
    }

    public void assertSubmitVisa() {
        waitFrameToBeAvailableAndSwitchToIt(By.id("vcop-src-frame"));
        Assertions.assertEquals(TestData.VISA_MESSAGE, paymentContainer.getText());
        getDriver().switchTo().defaultContent();
    }
}
