package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class PasswordProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)  {
        return Stream.of(
                Arguments.of("", "", "",
                        xpaths,
                        List.of(errorMessages.get(0), errorMessages.get(1), errorMessages.get(3), errorMessages.get(4))),
                Arguments.of("Gard3ner#", "Gard3ner#", "",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(3))),
                Arguments.of("", "NewPassword123!", "NewPassword123!",
                        List.of(xpaths.get(0)),
                        List.of(errorMessages.get(0))),
                Arguments.of("Gard3ner#", "WithoutSpecial", "WithoutSpecial",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "withoutuppercase", "withoutuppercase",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "WITHOUTLOWERCASE", "WITHOUTLOWERCASE",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "1234567890", "1234567890",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "Password123", "Password123",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "Password!", "Password!",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(2))),
                Arguments.of("Gard3ner#", "", "",
                        List.of(xpaths.get(1), xpaths.get(3)),
                        List.of(errorMessages.get(1), errorMessages.get(4))),
                Arguments.of("", "NewPassword123!", "",
                        List.of(xpaths.get(0), xpaths.get(3)),
                        List.of(errorMessages.get(0), errorMessages.get(4))),
                Arguments.of("", "", "NewPassword123!",
                        List.of(xpaths.get(0), xpaths.get(1), xpaths.get(2), xpaths.get(3)),
                        List.of(errorMessages.get(0), errorMessages.get(1), errorMessages.get(3), errorMessages.get(6))),
                Arguments.of("Gard3ner#", "Gard3ner#", "Gard3ner#",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(3))),
                Arguments.of("", "", "Gard3ner#",
                        List.of(xpaths.get(0), xpaths.get(1), xpaths.get(2), xpaths.get(3)),
                        List.of(errorMessages.get(0), errorMessages.get(1), errorMessages.get(3), errorMessages.get(6))),
                Arguments.of("Gard3ner#", "", "Gard3ner#",
                        List.of(xpaths.get(1), xpaths.get(3)),
                        List.of(errorMessages.get(1), errorMessages.get(6))),
                Arguments.of("Gard3ner#", "short", "short",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(5))),
                Arguments.of("Gard3ner#", "VeryLongPasswordThatExceeds20Characters", "VeryLongPasswordThatExceeds20Characters",
                        List.of(xpaths.get(1)),
                        List.of(errorMessages.get(5))),
                Arguments.of("Gard3ner#", "NewPassword123!", "TestPassword123!",
                        List.of(xpaths.get(3)),
                        List.of(errorMessages.get(6))));
//                Arguments.of("Gard3ner#", "password123!", "password123!",
//                        List.of(xpaths.get(1)),
//                        List.of(errorMessages.get(5))),
//                Arguments.of("Gard3ner#", "PASSWORD123!", "PASSWORD123!",
//                        List.of(xpaths.get(1)),
//                        List.of(errorMessages.get(5))),
    }
    List<String> errorMessages = List.of(
            "Введіть старий пароль",
            "Будь ласка, введіть новий пароль",
            "Пароль повинен містити великі/маленькі літери латинського алфавіту, цифри та спеціальні символи",
            "Значення поля ‘Новий пароль’ має відрізнятися від значення поля ‘Старий пароль’",
            "Будь ласка, підтвердіть пароль",
            "Пароль не може бути коротшим, ніж 8 та довшим, ніж 20 символів",
            "'Значення поля ‘Підтвердити новий пароль’ має бути еквівалентним значенню поля ‘Новий пароль’"
    );

    List<String> xpaths = List.of(
            "//div[@id='edit_currentPassword_help']//div[1]",
            "//div[@id='edit_password_help']//div[1]",
            "//div[@id='edit_password_help']//div[2]",
            "//div[@id='edit_confirmPassword_help']//div[1]"
    );
}
