package com.softserve.edu.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.softserve.edu.manager.Configuration.DEFAULT_EMAIL;
import static com.softserve.edu.manager.Configuration.DEFAULT_PASSWORD;
import static com.softserve.edu.runner.TestData.EMAIL;
import static com.softserve.edu.runner.TestData.PASSWORD;

public class CommentArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("yagifij495@eqvox.com", "Qwerty_1", "IT освіта: курси", "Проба Коментар"),
                Arguments.of(DEFAULT_EMAIL, DEFAULT_PASSWORD, "Онлайн-школа точних наук", "User Коментар")
        );
    }
}
