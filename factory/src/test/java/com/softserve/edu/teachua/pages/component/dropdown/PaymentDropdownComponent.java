package com.softserve.edu.teachua.pages.component.dropdown;

import com.softserve.edu.teachua.pages.component.option.PaymentOption;
import com.softserve.edu.teachua.pages.component.option.factory.MenuContents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentDropdownComponent extends DropdownComponent<PaymentDropdownComponent> {

    public PaymentDropdownComponent(WebDriver driver) {
        super(driver, PaymentOption.values());
    }

    public PaymentDropdownComponent(WebDriver driver, PaymentOption[] options) {
        super(driver, options);
    }

    public void selectOption(MenuContents<PaymentDropdownComponent> option) {
        factory.selectOption(this, option);
    }

    @FindBy(id = "paymenu-dropdown-p24-button")
    private WebElement privat24PaymentOption;

    @FindBy(id = "paymenu-dropdown-lqpt-button")
    private WebElement terminalPaymentOption;

    @FindBy(id = "paymenu-dropdown-master-button")
    private WebElement masterPassPaymentOption;

    @FindBy(id = "paymenu-dropdown-visa-button")
    private WebElement visaCheckoutPaymentOption;

    @FindBy(xpath = "//a[@id='paymenu-selector']")
    private WebElement payMenuSelector;


    public void clickCardPaymentOption() {
        payMenuSelector.click();
    }

    public void clickMasterPassPaymentOption() {
        masterPassPaymentOption.click();
    }

    public void clickPrivat24PaymentOption() {
        privat24PaymentOption.click();
    }

    public void clickTerminalPaymentOption() {
        terminalPaymentOption.click();
    }

    public void clickVisaCheckoutPaymentOption() {
        visaCheckoutPaymentOption.click();
    }
}
