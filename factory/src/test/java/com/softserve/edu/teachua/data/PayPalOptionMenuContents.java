package com.softserve.edu.teachua.data;

import com.softserve.edu.teachua.pages.component.dropdown.PaymentDropdownComponent;
import com.softserve.edu.teachua.pages.component.option.factory.MenuContents;

public enum PayPalOptionMenuContents implements MenuContents<PaymentDropdownComponent> {
    CREDIT_CARD("", "https://secure.wayforpay.com/payment/s0f2891d77061#tab-card", "#cardpay .control-label",
            "Номер картки", "https://secure.wayforpay.com/img/method/visamcpr.png?v=4"),
    PRIVAT_CARD("Оплата через Приват24", "https://secure.wayforpay.com/payment/s0f2891d77061#tab-p24", "#payp24 .notes",
            "Ви обрали спосіб оплати через інтернет-банкінг Приват24.",
            "https://secure.wayforpay.com/img/method/p24.png"),
    LIQUID_CARD("Оплата готівкою через термінал", "https://secure.wayforpay.com/payment/s0f2891d77061#tab-lqpt",
            "#paylqpt .notes",
            "Ви обрали спосіб оплати через термінал. Після натискання кнопки \"Оплатити\" буде створено код, за яким Ви зможете зробити оплату в терміналі ПриватБанку.",
            "https://secure.wayforpay.com/img/method/terminal.small.png"),
    MASTER_CARD("MasterPass", "https://secure.wayforpay.com/payment/s0f2891d77061#tab-master", "#mp-block-auth .description",
            "Masterpass – електронний гаманець від MasterCard, що зберігає Ваші платіжні дані у безпечному місці.",
            "https://secure.wayforpay.com/img/method/masterpass.png"),
    VISA_CARD("Visa Checkout", "https://secure.wayforpay.com/payment/s0f2891d77061#tab-visa", "#vcpay-block-submit .notes",
            "Для продовження оплати заповніть всі необхідні поля",
            "https://secure.wayforpay.com/img/method/visa-checkout-inline2.png");

    private final String optionName;
    private final String url;
    private final String selector;
    private final String expectedText;
    private final String expectedIconUrl;

    PayPalOptionMenuContents(String optionName, String url, String selector, String expectedText, String expectedIconUrl) {
        this.optionName = optionName;
        this.url = url;
        this.selector = selector;
        this.expectedText = expectedText;
        this.expectedIconUrl = expectedIconUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getSelector() {
        return selector;
    }

    public String getExpectedText() {
        return expectedText;
    }

    public String getExpectedIconUrl() {
        return expectedIconUrl;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }
}
