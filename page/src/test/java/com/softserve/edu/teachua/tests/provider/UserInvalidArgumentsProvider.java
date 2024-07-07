package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.entity.Comment;
import com.softserve.edu.teachua.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserInvalidArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new User("user@gmail.com", "User_1!",
                        new Comment("Онлайн-школа", "Коментар"))),
                Arguments.of(new User("hahaha@gmail.com", "Qwerty_1",
                        new Comment("IT освіта", "Проба")))
        );
    }
}
