package com.softserve.edu.teachua.pages.main;

import com.softserve.edu.teachua.pages.component.form.PaymentFormComponent;
import com.softserve.edu.teachua.pages.top.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.softserve.edu.teachua.data.TestData.COMMON_HEADER;
import static com.softserve.edu.teachua.data.TestData.HELP_US_URL;

public class HelpUsPage extends BasePage<HelpUsPage> {

    @FindBy(xpath = "//a[@id='paymenu-selector']")
    private WebElement payMenuSelector;

    @FindBy(id = "freepay-amount")
    private WebElement freePayAmountInput;

    public HelpUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public HelpUsPage assertCurrentURL() {
        Assertions.assertTrue(isCurrentURLContains(HELP_US_URL),
                "Current URL should match expected HELP_US_URL");
        return this;
    }

    public HelpUsPage assertCurrentTitle() {
        Assertions.assertEquals(COMMON_HEADER, getDriver().getTitle(),
                String.format("Expected page title to be '%s' but found '%s'", COMMON_HEADER, getDriver().getTitle()));
        return this;
    }

    @Override
    public HelpUsPage visit() {
        getDriver().get(HELP_US_URL);
        return this;
    }

    public PaymentFormComponent openOtherWayPayment() {
        clickPayMenuSelector();
        return new PaymentFormComponent(getDriver());
    }

    private void clickPayMenuSelector() {
        waitForClickableOfElement(payMenuSelector).click();
    }

    public HelpUsPage fillAmount(String amount) {
        fillAmount(freePayAmountInput, amount);
        return new HelpUsPage(getDriver());
    }
}
