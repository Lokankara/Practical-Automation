package com.softserve.edu.teachua.pages.component.option;

import com.softserve.edu.teachua.pages.component.dropdown.PaymentDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.factory.OptionHandler;

import java.util.function.Consumer;

public enum PaymentOption implements OptionHandler<PaymentDropdownComponent> {
    PRIVAT_24("Оплата через Приват24", PaymentDropdownComponent::clickPrivat24PaymentOption),
    TERMINAL("Оплата готівкою через термінал", PaymentDropdownComponent::clickTerminalPaymentOption),
    MASTER_PASS("MasterPass", PaymentDropdownComponent::clickMasterPassPaymentOption),
    VISA_CHECKOUT("Visa Checkout", PaymentDropdownComponent::clickVisaCheckoutPaymentOption),
    CREDIT_CARD("Оплата кредитною карткою", PaymentDropdownComponent::clickCardPaymentOption);

    private final String optionName;
    private final Consumer<PaymentDropdownComponent> clickAction;

    PaymentOption(String optionName, Consumer<PaymentDropdownComponent> clickAction) {
        this.optionName = optionName;
        this.clickAction = clickAction;
    }

    public String getOptionName() {
        return optionName;
    }

    @Override
    public Consumer<PaymentDropdownComponent> getHandler() {
        return clickAction;
    }
}
