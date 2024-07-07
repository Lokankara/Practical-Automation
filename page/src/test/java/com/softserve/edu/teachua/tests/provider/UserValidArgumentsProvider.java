package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.entity.Comment;
import com.softserve.edu.teachua.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserValidArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new User("nipibaw191@fresec.com", "Secret123!",
                        new Comment("Dream Team", "Great teamwork during activities!"))),
                Arguments.of(new User("lijoyiv225@hutov.com", "Gard3ner#",
                        new Comment("YOUSTUD", "Always willing to help others."))),
                Arguments.of(new User("nenix55377@hutov.com", "Elv3nWay!",
                        new Comment("LESKIV-SCHOOL", "Demonstrates good sportsmanship."))),
                Arguments.of(new User("yagifij495@eqvox.com", "Qwerty_1",
                        new Comment("IT освіта: курси", "Проба Коментар"))),
                Arguments.of(new User("wereken128@hutov.com", "R1ngBearer!",
                        new Comment("Онлайн-школа точних наук", "User Коментар")))
        );
    }
}
