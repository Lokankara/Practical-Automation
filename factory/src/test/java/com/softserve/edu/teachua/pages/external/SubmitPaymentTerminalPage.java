package com.softserve.edu.teachua.pages.external;

import com.softserve.edu.teachua.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SubmitPaymentTerminalPage extends PaymentPage {
    public SubmitPaymentTerminalPage(WebDriver driver) {
        super(driver);
        isCurrentURLContains(TestData.TERMINAL_URL);
        Assertions.assertEquals("LiqPay Wayforpay LLC", getDriver().getTitle());
    }

    public void assertSubmitTerminal() {
        TestData.ERROR_TERMINAL_MESSAGES.forEach(expectedMessage ->
                Assertions.assertTrue(getDriver().findElement(By.id("Conteiner")).getText().contains(expectedMessage),
                        String.format("Expected message '%s' not found in wrapper text", expectedMessage))
        );
    }
}