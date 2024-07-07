package com.softserve.edu.teachua.data;

import com.softserve.edu.teachua.pages.component.dropdown.PaymentDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.factory.MenuContents;

public enum PaymentMenuContents implements MenuContents<PaymentDropdownComponent> {
    DEFAULT_CARD("Оплата кредитною карткою", "cardpay"),
    PRIVAT_24("Оплата через Приват24", "payp24"),
    TERMINAL("Оплата готівкою через термінал", "paylqpt"),
    MASTER_PASS("MasterPass", "mpauth"),
    VISA("Visa Checkout", "vcpay");

    private final String optionName;
    private final String locatorId;

    PaymentMenuContents(String optionName, String locatorId) {
        this.optionName = optionName;
        this.locatorId = locatorId;
    }

    public String getOptionName() {
        return optionName;
    }

    @Override
    public String getSelector() {
        return locatorId;
    }
}
