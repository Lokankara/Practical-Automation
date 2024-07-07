package com.softserve.edu.teachua.pages.external;

import com.softserve.edu.teachua.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubmitPaymentPrivatePage extends PaymentPage {

    @FindBy(css = "#login iframe")
    private WebElement iframe;

    @FindBy(xpath = "//h2[contains(text(),'Вхід/Реєстрація')]")
    private WebElement header;

    public SubmitPaymentPrivatePage(WebDriver driver) {
        super(driver);
        isCurrentURLContains(TestData.PRIVATE_URL);
        Assertions.assertEquals("Privat24", getDriver().getTitle());
    }

    public void assertSubmitPrivat() {
        getDriver().switchTo().frame(iframe);
        Assertions.assertEquals("Вхід/Реєстрація", header.getText());
        getDriver().switchTo().defaultContent();
    }
}
