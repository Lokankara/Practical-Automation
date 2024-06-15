package org.softserve.academy.provider;

import java.util.List;

public class UserInfoData {
    public static final String PHONE = "0123456789";
    public static final String LAST_NAME = "LastName";
    public static final String FIRST_NAME = "FirstName";
    public static final String CURRENT_PASSWORD = "Gard3ner#";
    public static final String NEW_PASSWORD = "NewPassword123!";
    public static final String WRONG_PASSWORD = "TestPassword123!";
    public static final String MIN_LENGTH = "Pass!1";
    public static final String MAX_LENGTH = "ThisIsAVeryLongPasswordThatExceedsTheMaxLength";

    public static final List<String> userLabelXPaths = List.of(
            "//div[@id='edit_lastName_help']",
            "//div[@id='edit_firstName_help']",
            "//div[@id='edit_phone_help']//div[1]",
            "//div[@id='edit_phone_help']//div[2]"
    );

    public static final List<String> passwordLabelXPaths = List.of(
            "//div[@id='edit_currentPassword_help']//div[1]",
            "//div[@id='edit_password_help']//div[1]",
            "//div[@id='edit_password_help']//div[2]",
            "//div[@id='edit_confirmPassword_help']//div[1]"
    );

    public static final List<String> userErrorMessages = List.of(
            "Будь ласка введіть Ваше прізвище",
            "Прізвище повинно починатися і закінчуватися літерою",
            "Прізвище не може містити спеціальні символи",
            "Прізвище не може містити цифри",
            "Прізвище не може містити більше, ніж 25 символів",
            "Введіть Ваше ім'я",
            "Ім'я повинно починатися та закінчуватися літерою",
            "Ім'я не може містити спеціальні символи",
            "Ім'я не може містити цифри",
            "Ім'я не може містити більше, ніж 25 символів",
            "Будь ласка введіть Ваш номер телефону",
            "Телефон не може містити пробіли",
            "Телефон не відповідає вказаному формату",
            "Телефон не може містити спеціальні символи",
            "Телефон не може містити літери",
            "Телефон не може містити більше, ніж 10 цифр"
    );

    public static final List<String> passwordErrorMessages = List.of(
            "Введіть старий пароль",
            "Будь ласка, введіть новий пароль",
            "Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи",
            "Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’",
            "Будь ласка, підтвердіть пароль",
            "Пароль не може бути коротшим, ніж 8 та довшим, ніж 20 символів",
            "'Значення поля ‘Підтвердити новий пароль’ має бути еквівалентним значенню поля ‘Новий пароль’"
    );
}
