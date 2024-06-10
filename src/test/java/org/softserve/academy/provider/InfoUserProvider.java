package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class InfoUserProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        List<String> messages = List.of(
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

        List<String> xpaths = List.of(
                "//div[@id='edit_lastName_help']",
                "//div[@id='edit_firstName_help']",
                "//div[@id='edit_phone_help']//div[1]",
                "//div[@id='edit_phone_help']//div[2]"
        );

        return Stream.of(
                Arguments.of("", "", "",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(0), messages.get(1), messages.get(2))),
                Arguments.of("", "FirstName", "0123456789",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(0))),
                Arguments.of(" ", "FirstName", "0123456789",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(1))),
                Arguments.of("!", "FirstName", "0123456789",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(2))),
                Arguments.of("1", "FirstName", "0123456789",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(3))),
                Arguments.of("a".repeat(26), "FirstName", "0123456789",
                        List.of(xpaths.get(0)),
                        List.of(messages.get(4))),
                Arguments.of("LastName", "", "0123456789",
                        List.of(xpaths.get(1)),
                        List.of(messages.get(5))),
                Arguments.of("LastName", " ", "0123456789",
                        List.of(xpaths.get(1)),
                        List.of(messages.get(6))),
                Arguments.of("LastName", "!", "0123456789",
                        List.of(xpaths.get(1)),
                        List.of(messages.get(7))),
                Arguments.of("LastName", "1", "0123456789",
                        List.of(xpaths.get(1)),
                        List.of(messages.get(8))),
                Arguments.of("LastName", "a".repeat(26), "0123456789",
                        List.of(xpaths.get(1)),
                        List.of(messages.get(9))),
                Arguments.of("LastName", "FirstName", "",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(10))),
                Arguments.of("LastName", "FirstName", " ",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(11))),
                Arguments.of("LastName", "FirstName", "a",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(14))),
                Arguments.of("LastName", "FirstName", "1",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(12))),
                Arguments.of("LastName", "FirstName", "!",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(12))),
                Arguments.of("LastName", "FirstName", "123456789",
                        List.of(xpaths.get(2)),
                        List.of(messages.get(12)))
        );
    }
}