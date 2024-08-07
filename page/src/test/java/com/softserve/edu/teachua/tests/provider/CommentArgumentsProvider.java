package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.entity.Comment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CommentArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(new Comment("IT освіта: курси", "Проба Коментар")),
                Arguments.of(new Comment("Онлайн-школа точних наук", "User Коментар")));
    }
}
