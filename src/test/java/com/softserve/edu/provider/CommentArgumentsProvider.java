package com.softserve.edu.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CommentArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("yagifij495@eqvox.com", "Qwerty_1", "IT освіта: курси", "Проба Коментар"),
                Arguments.of("user@gmail.com", "user", "Онлайн-школа точних наук", "User Коментар")
        );
    }
}
