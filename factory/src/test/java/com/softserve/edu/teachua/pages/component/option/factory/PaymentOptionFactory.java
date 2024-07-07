package com.softserve.edu.teachua.pages.component.option.factory;

import com.softserve.edu.teachua.pages.component.dropdown.PaymentDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.PaymentOption;

public class PaymentOptionFactory extends OptionFactory<PaymentDropdownComponent> {
    public PaymentOptionFactory() {
        super(PaymentOption.values());
    }
}
