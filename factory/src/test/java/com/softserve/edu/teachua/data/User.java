package com.softserve.edu.teachua.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum User {
    JACK(PaymentMenuContents.DEFAULT_CARD, "6", "4242424242424242", "123",
            "Jack Sparrow", "+380987654321", "Jack.Sparrow@i.ua",
            LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM/yy"))),
    WILL(PaymentMenuContents.DEFAULT_CARD, "5", "4111111111111111", "321",
            "Will Turner", "+1987654321", "will.turner@example.com",
            LocalDateTime.now().plusMonths(2).format(DateTimeFormatter.ofPattern("MM/yy"))),

    ELIZABETH(PaymentMenuContents.DEFAULT_CARD, "7", "4242424242424242", "456",
            "Elizabeth Swann", "+1654321876", "elizabeth.swann@example.com",
            LocalDateTime.now().plusMonths(3).format(DateTimeFormatter.ofPattern("MM/yy")));

    private final PaymentMenuContents paymentOption;
    private final String amount;
    private final String cardNumber;
    private final String cvv;
    private final String fullName;
    private final String phone;
    private final String email;
    private final String expiredAt;

    User(PaymentMenuContents paymentOption, String amount, String cardNumber, String cvv, String fullName, String phone, String email, String expiredAt) {
        this.amount = amount;
        this.cvv = cvv;
        this.paymentOption = paymentOption;
        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.expiredAt = expiredAt;
    }

    public String amount() {
        return amount;
    }

    public PaymentMenuContents paymentOption() {
        return paymentOption;
    }

    public String cardNumber() {
        return cardNumber;
    }

    public String fullName() {
        return fullName;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    public String expiredAt() {
        return expiredAt;
    }

    public String cvv() {
        return cvv;
    }
}
