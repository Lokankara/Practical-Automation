package com.softserve.edu.teachua.pages.component.form;

import com.softserve.edu.teachua.data.PayPalOptionMenuContents;
import com.softserve.edu.teachua.data.PaymentMenuContents;
import com.softserve.edu.teachua.pages.component.dropdown.CurrencyDropdownComponent;
import com.softserve.edu.teachua.pages.component.dropdown.PaymentDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.PaymentOption;
import com.softserve.edu.teachua.pages.component.option.factory.DropdownOption;
import com.softserve.edu.teachua.pages.component.option.factory.MenuContents;
import com.softserve.edu.teachua.pages.external.GoogleSignInPage;
import com.softserve.edu.teachua.pages.external.SubmitPaymentMasterPage;
import com.softserve.edu.teachua.pages.external.SubmitPaymentPage;
import com.softserve.edu.teachua.pages.external.SubmitPaymentPrivatePage;
import com.softserve.edu.teachua.pages.external.SubmitPaymentTerminalPage;
import com.softserve.edu.teachua.pages.external.SubmitPaymentVisaPage;
import com.softserve.edu.teachua.pages.top.BaseModel;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.IntStream;

public class PaymentFormComponent extends BaseModel {
    private final String originalWindowHandle;
    private String inputId;

    @FindBy(id = "pay-form-block")
    private WebElement paymentFormContainer;

    public PaymentFormComponent(WebDriver driver) {
        super(driver);
        this.originalWindowHandle = driver.getWindowHandle();
    }

    @FindBy(xpath = "//ul[@id='paymenu-dropdown']/li/a")
    private List<WebElement> paymentOptions;

    @FindBy(xpath = "//li[@id='paymenu-current']/img")
    private WebElement imgCurrentIcon;

    @FindBy(id = "gpay-button-online-api-id")
    private WebElement payButton;

    @FindBy(xpath = "//small[@class='help-block']")
    private WebElement helpBlockLabel;

    @FindBy(id = "freepay-amount")
    private WebElement freePayAmountInput;

    @FindBy(id = "freepay-currency")
    private WebElement currencyButton;

    private String getAttributeImage() {
        return imgCurrentIcon.getAttribute("src");
    }

    private String getHref(WebElement element) {
        return element.getAttribute("href");
    }

    private String getTitle(WebElement option) {
        return option.findElement(By.cssSelector("span.pull-right")).getText().trim();
    }

    public String getHelpBlockText() {
        return helpBlockLabel.getText();
    }

    public List<String> getPaymentOptionsText() {
        return paymentOptions
                .stream()
                .map(this::getTitle)
                .toList();
    }

    public List<String> getPaymentOptionsUrl() {
        return paymentOptions
                .stream()
                .map(this::getHref)
                .toList();
    }

    private WebElement getContainer(PayPalOptionMenuContents option) {
        return waitForPresenceOfElementLocated(By.cssSelector(option.getSelector()));
    }

    private List<String> getErrorInputTextLabel() {
        return paymentFormContainer.findElements(By.xpath(".//small[@class='help-block']"))
                .stream()
                .map(WebElement::getText)
                .filter(text -> text != null && !text.trim().isEmpty())
                .toList();
    }

    public GoogleSignInPage switchToGoogleWindow() {
        getDriver().getWindowHandles()
                .stream()
                .filter(windowHandle -> !windowHandle.equals(originalWindowHandle))
                .findFirst()
                .ifPresent(windowHandle -> getDriver().switchTo().window(windowHandle));
        return new GoogleSignInPage(getDriver());
    }

    public PaymentFormComponent selectPayment(MenuContents<PaymentDropdownComponent> option) {
        selectOption(option);
        inputId = option.getSelector();
        return this;
    }

    public PaymentFormComponent selectCurrency(DropdownOption<CurrencyDropdownComponent> option) {
        currencyButton.click();
        selectOption(option);
        return this;
    }

    private void selectOption(DropdownOption<CurrencyDropdownComponent> option) {
        new CurrencyDropdownComponent(getDriver()).select(option);
    }

    private void selectOption(MenuContents<PaymentDropdownComponent> option) {
        new PaymentDropdownComponent(getDriver(), PaymentOption.values()).selectOption(option);
    }

    public PaymentFormComponent fillPasswordInput(String password) {
        waitAndType(By.id(inputId + "-pwd"), password);
        return this;
    }

    public PaymentFormComponent fillCardNumberInput(String cardNumber) {
        fillInput(By.id(inputId + "-cardnumber"), cardNumber);
        return this;
    }

    public PaymentFormComponent fillNameInput(String fullName) {
        fillInput(By.id(inputId + "-name"), fullName);
        return this;
    }

    public PaymentFormComponent fillCVVInput(String cvv) {
        waitAndType(By.id(inputId + "-cardsecure"), cvv);
        return this;
    }

    public PaymentFormComponent fillCardValidityInput(String cardValidity) {
        fillInput(By.id(inputId + "-validity"), cardValidity);
        gotoNext();
        gotoPrev();
        return this;
    }

    public PaymentFormComponent fillCardPhoneInput(String phone) {
        waitAndType(By.id(inputId + "-clientphone"), phone);
        return this;
    }

    public PaymentFormComponent fillPhoneInput(String phone) {
        waitAndType(By.id(inputId + "-phone"), phone);
        return this;
    }

    public PaymentFormComponent fillCardEmailInput(String email) {
        waitAndType(By.id(inputId + "-clientemail"), email);
        gotoNext();
        return this;
    }

    public PaymentFormComponent fillEmailInput(String email) {
        waitAndType(By.id(inputId + "-email"), email);
        gotoNext();
        return this;
    }

    public PaymentFormComponent fillCardHolderInput(String fullName) {
        waitAndType(By.id(inputId + "-cardholder"), fullName);
        return this;
    }

    public PaymentFormComponent fillAmount(String amount) {
        fillAmount(freePayAmountInput, amount);
        return this;
    }

    public SubmitPaymentTerminalPage submitTerminalPayment() {
        waitForClickableOfElement(By.id(inputId + "-submit")).click();
        return new SubmitPaymentTerminalPage(getDriver());
    }

    public SubmitPaymentMasterPage submitMasterPassPayment() {
        waitForClickableOfElement(By.id(inputId + "-submit")).click();
        return new SubmitPaymentMasterPage(getDriver());
    }

    public PaymentFormComponent submitNext() {
        waitForClickableOfElement(By.id(inputId + "-submit")).click();
        return this;
    }

    public SubmitPaymentPage submitPayment() {
        waitForClickableOfElement(By.id(inputId + "-submit")).click();
        return new SubmitPaymentPage(getDriver());
    }

    public SubmitPaymentPrivatePage submitPrivatPayment() {
        waitForClickableOfElement(By.id(inputId + "-submit")).click();
        return new SubmitPaymentPrivatePage(getDriver());
    }

    public SubmitPaymentVisaPage submitVisaPayment() {
        waitForClickableOfElement(By.id("visa-checkout-button")).click();
        return new SubmitPaymentVisaPage(getDriver());
    }

    public PaymentFormComponent submitGPayment() {
        payButton.click();
        return this;
    }

    public boolean isHelpBlockDisplayed() {
        return helpBlockLabel.isDisplayed();
    }

    public boolean isDisplayedPayButton() {
        return payButton.isDisplayed();
    }

    public PaymentFormComponent checkEmptyErrorMessages() {
        Assertions.assertTrue(getErrorInputTextLabel().isEmpty());
        return this;
    }

    public void assertOptionsText(PayPalOptionMenuContents option) {
        assertIconUrl(option);
        assertText(option);
    }

    private void assertText(PayPalOptionMenuContents option) {
        WebElement element = getContainer(option);
        Assertions.assertEquals(option.getExpectedText(), element.getText(),
                String.format("Text does not match expected for option '%s'. Expected: '%s', Actual: '%s'",
                        option.getOptionName(), option.getExpectedText(), element.getText()));
    }

    private void assertIconUrl(PayPalOptionMenuContents option) {
        Assertions.assertEquals(option.getExpectedIconUrl(), getAttributeImage(),
                String.format("Icon URL does not match expected for option '%s'. Expected: '%s', Actual: '%s'",
                        option.getOptionName(), option.getExpectedIconUrl(), getAttributeImage()));
    }

    public PaymentFormComponent assertOptionsIsContains(PayPalOptionMenuContents option) {
        Assertions.assertTrue(getPaymentOptionsText().contains(option.getOptionName()),
                String.format("Payment option '%s' is not present in the dropdown menu", option.getOptionName()));
        return this;
    }

    public PaymentFormComponent assertPaymentOptionsText(PayPalOptionMenuContents[] menuContents) {
        List<String> paymentOptionsText = getPaymentOptionsText();
        IntStream.range(0, paymentOptionsText.size()).forEach(i ->
                Assertions.assertEquals(menuContents[i].getOptionName(), paymentOptionsText.get(i),
                        String.format("Title mismatch for payment option '%s'. Expected: '%s', Actual: '%s'",
                                paymentOptionsText.get(i), menuContents[i].getOptionName(), paymentOptionsText.get(i))));
        return this;
    }

    public PaymentDropdownComponent assertPaymentOptionsUrl(PayPalOptionMenuContents[] menuContents) {
        List<String> paymentOptionsUrl = getPaymentOptionsUrl();
        IntStream.range(0, paymentOptionsUrl.size()).forEach(i ->
                Assertions.assertEquals(menuContents[i].getUrl(), paymentOptionsUrl.get(i),
                        String.format("URL mismatch for payment option '%s'. Expected: '%s', Actual: '%s'",
                                getPaymentOptionsText().get(i), menuContents[i].getUrl(), paymentOptionsUrl.get(i))));
        return new PaymentDropdownComponent(getDriver());
    }

    public void assertFactoryOptions(PayPalOptionMenuContents[] menuContents, PaymentMenuContents[] paymentMenuContents) {
        IntStream.range(0, paymentMenuContents.length).forEach(i ->
                assertOptionsIsContains(menuContents[i])
                        .selectPayment(paymentMenuContents[i])
                        .assertPaymentOptionsUrl(menuContents)
                        .clickCardPaymentOption());
    }

    public PaymentFormComponent assertHelpBlockLabel(String expected) {
        Assertions.assertTrue(isHelpBlockDisplayed(), "Help block label is not displayed");
        Assertions.assertEquals(expected, getHelpBlockText(), "The help block text does not match the expected value.");
        return this;
    }

    public void assertFreePayAmountInputHasRedBorder() {
        String borderColor = freePayAmountInput.getCssValue("border-color");
        String expectedBorderColor = "rgb(231, 31, 21)";
        Assertions.assertEquals(expectedBorderColor, borderColor, "Free pay amount input does not have a red border");
    }

    public void assertPaymentAmountMessage(String amount, String message) {
        fillAmount(amount)
                .submitGPayment()
                .assertHelpBlockLabel(message)
                .assertFreePayAmountInputHasRedBorder();
    }

    public void assertErrorInputMessageLabels(List<String> expectedErrorMessages) {
        for (String message : getErrorInputTextLabel()) {
            Assertions.assertTrue(expectedErrorMessages.contains(message),
                    String.format("Expected error message %s, not found: %s", message, expectedErrorMessages));
        }
    }
}
