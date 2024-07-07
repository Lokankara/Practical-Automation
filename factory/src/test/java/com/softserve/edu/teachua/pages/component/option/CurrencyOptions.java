package com.softserve.edu.teachua.pages.component.option;

import com.softserve.edu.teachua.pages.component.dropdown.CurrencyDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.factory.OptionHandler;

import java.util.function.Consumer;

public enum CurrencyOptions implements OptionHandler<CurrencyDropdownComponent> {
    UAH("UAH", CurrencyDropdownComponent::clickUAH),
    USD("USD", CurrencyDropdownComponent::clickUSD),
    EUR("EUR", CurrencyDropdownComponent::clickEUR);

    private final String optionName;
    private final Consumer<CurrencyDropdownComponent> handler;

    CurrencyOptions(String optionName, Consumer<CurrencyDropdownComponent> handler) {
        this.optionName = optionName;
        this.handler = handler;
    }

    public String getOptionName() {
        return optionName;
    }

    public Consumer<CurrencyDropdownComponent> getHandler() {
        return handler;
    }
}
