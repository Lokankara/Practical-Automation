package com.softserve.edu.teachua.pages.component.option.factory;

import com.softserve.edu.teachua.pages.component.dropdown.CurrencyDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.CurrencyOptions;

public class CurrencyOptionFactory extends OptionFactory<CurrencyDropdownComponent> {
    public CurrencyOptionFactory() {
        super(CurrencyOptions.values());
    }
}
