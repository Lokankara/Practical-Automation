package com.softserve.edu.teachua.pages.external;

import com.softserve.edu.teachua.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.softserve.edu.teachua.data.TestData.PAYMENT;

public class SubmitPaymentPage extends PaymentPage {

    @FindBy(css = ".block-result")
    private WebElement resultWrapper;

    public SubmitPaymentPage(WebDriver driver) {
        super(driver);
        isCurrentURLContains(PAYMENT);
    }

    private String getWrapperText() {
        return resultWrapper.getText();
    }

    public void assertSubmit() {
        TestData.ERROR_EXPECTED_MESSAGES.forEach(expectedMessage ->
                Assertions.assertTrue(getWrapperText().contains(expectedMessage),
                        String.format("Expected message '%s' not found in wrapper text, but was %s", expectedMessage, getWrapperText()))
        );
    }
}
