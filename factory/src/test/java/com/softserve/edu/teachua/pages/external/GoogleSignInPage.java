package com.softserve.edu.teachua.pages.external;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleSignInPage extends PaymentPage {
    public GoogleSignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "h1")
    private WebElement headerLabel;

    @FindBy(id = "headingSubtext")
    private WebElement headingSubtextLabel;


    public void assertHeader() {
        Assertions.assertEquals("Sign in", headerLabel.getText());
        Assertions.assertEquals("Use your Google Account", headingSubtextLabel.getText());
    }
}
