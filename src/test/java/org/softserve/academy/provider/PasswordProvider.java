package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class PasswordProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("", "", "",
                        List.of(
                                "//div[@id='edit_currentPassword_help']//div[1]",
                                "//div[@id='edit_password_help']//div[1]",
                                "//div[@id='edit_password_help']//div[2]",
                                "//div[@id='edit_confirmPassword_help']//div[1]"),
                        List.of(
                                "Введіть старий пароль",
                                "Будь ласка, введіть новий пароль",
                                "Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’",
                                "Будь ласка, підтвердіть пароль")),
//                Arguments.of("Gard3ner#", "Gard3ner#", "",
//                        List.of("//div[@id='edit_password_help']//div[1]"),
//                        List.of("Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’")),
//                Arguments.of("", "NewPassword123!", "NewPassword123!",
//                        List.of("//div[@id='edit_currentPassword_help']//div[1]", "//div[@id='edit_password_help']//div[1]"),
//                        List.of("Введіть старий пароль", "Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’")),

                Arguments.of("Gard3ner#", "WithoutSpecial", "WithoutSpecial",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "withoutuppercase", "withoutuppercase",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "WITHOUTLOWERCASE", "WITHOUTLOWERCASE",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "1234567890", "1234567890",
                        List.of("//div[@id='edit_password_help']//div[1]", "//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи", "Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "Password123", "Password123",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "Password!", "Password!",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи")),
                Arguments.of("Gard3ner#", "", "",
                        List.of("//div[@id='edit_password_help']//div[1]", "//div[@id='edit_confirmPassword_help']//div[1]"),
                        List.of("Будь ласка, введіть новий пароль", "Будь ласка, підтвердіть пароль")),
                Arguments.of("", "NewPassword123!", "",
                        List.of("//div[@id='edit_currentPassword_help']//div[1]", "//div[@id='edit_confirmPassword_help']//div[1]"),
                        List.of("Введіть старий пароль", "Будь ласка, підтвердіть пароль")),
                Arguments.of("", "", "NewPassword123!",
                        List.of("//div[@id='edit_currentPassword_help']//div[1]", "//div[@id='edit_password_help']//div[1]"),
                        List.of("Введіть старий пароль", "Будь ласка, введіть новий пароль")),
                Arguments.of("Gard3ner#", "Gard3ner#", "Gard3ner#",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’")),
                Arguments.of("", "", "Gard3ner#",
                        List.of("//div[@id='edit_currentPassword_help']//div[1]", "//div[@id='edit_password_help']//div[1]"),
                        List.of("Введіть старий пароль", "Будь ласка, введіть новий пароль")),
                Arguments.of("Gard3ner#", "", "Gard3ner#",
                        List.of("//div[@id='edit_password_help']//div[1]", "//div[@id='edit_confirmPassword_help']//div[1]"),
                        List.of("Будь ласка, введіть новий пароль", "'Значення поля ‘Підтвердити новий пароль’ має бути еквівалентним значенню поля ‘Новий пароль’")),
                Arguments.of("Gard3ner#", "short", "short",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль не може бути коротшим, ніж 8 та довшим, ніж 20 символів")),
                Arguments.of("Gard3ner#", "VeryLongPasswordThatExceeds20Characters", "VeryLongPasswordThatExceeds20Characters",
                        List.of("//div[@id='edit_password_help']//div[1]"),
                        List.of("Пароль не може бути коротшим, ніж 8 та довшим, ніж 20 символів")),
                Arguments.of("Gard3ner#", "NewPassword123!", "TestPassword123!",
                        List.of("//div[@id='edit_confirmPassword_help']//div[1]"),
                        List.of("'Значення поля ‘Підтвердити новий пароль’ має бути еквівалентним значенню поля ‘Новий пароль’"))
//                Arguments.of("Gard3ner#", "password123!", "password123!",
//                        List.of("//div[@id='edit_password_help']//div[1]"),
//                        List.of("Пароль повинен містити великі літери")),
//                Arguments.of("Gard3ner#", "PASSWORD123!", "PASSWORD123!",
//                        List.of("//div[@id='edit_password_help']//div[1]"),
//                        List.of("Пароль повинен містити маленькі літери")),
        );
    }
}
