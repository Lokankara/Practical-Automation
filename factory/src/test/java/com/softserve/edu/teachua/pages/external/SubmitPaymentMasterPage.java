package com.softserve.edu.teachua.pages.external;

import com.softserve.edu.teachua.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubmitPaymentMasterPage extends PaymentPage {
    @FindBy(xpath = "//div[@id='mp-block-auth-broken']//h4")
    private WebElement headerLabel;

    @FindBy(xpath = "//div[@id='mp-block-auth-broken']//div[@class='description']")
    private WebElement descriptionLabel;

    public SubmitPaymentMasterPage(WebDriver driver) {
        super(driver);
    }

    public void assertSubmitMasterPass() {
        waitForClickableOfElement(By.cssSelector(".btn.btn-wfp.btn-cancel"));

        Assertions.assertEquals(TestData.EXPECTED_MASTER_PASS_HEADER, headerLabel.getText().trim(),
                "Header label text does not match expected");
        Assertions.assertEquals(TestData.EXPECTED_MASTER_PASS_DESCRIPTION, descriptionLabel.getText().trim(),
                "Description label text does not match expected");
    }
}
