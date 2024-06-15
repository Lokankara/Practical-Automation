package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

import static org.softserve.academy.provider.UserInfoData.FIRST_NAME;
import static org.softserve.academy.provider.UserInfoData.LAST_NAME;
import static org.softserve.academy.provider.UserInfoData.PHONE;
import static org.softserve.academy.provider.UserInfoData.userErrorMessages;
import static org.softserve.academy.provider.UserInfoData.userLabelXPaths;

public class InfoUserProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

        return Stream.of(
                Arguments.of("", "", "",
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(0), userErrorMessages.get(1), userErrorMessages.get(2))),
                Arguments.of("", FIRST_NAME, PHONE,
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(0))),
                Arguments.of(" ", FIRST_NAME, PHONE,
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(1))),
                Arguments.of("!", FIRST_NAME, PHONE,
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(2))),
                Arguments.of("1", FIRST_NAME, PHONE,
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(3))),
                Arguments.of("a".repeat(26), FIRST_NAME, PHONE,
                        List.of(userLabelXPaths.get(0)),
                        List.of(userErrorMessages.get(4))),
                Arguments.of(LAST_NAME, "", PHONE,
                        List.of(userLabelXPaths.get(1)),
                        List.of(userErrorMessages.get(5))),
                Arguments.of(LAST_NAME, " ", PHONE,
                        List.of(userLabelXPaths.get(1)),
                        List.of(userErrorMessages.get(6))),
                Arguments.of(LAST_NAME, "!", PHONE,
                        List.of(userLabelXPaths.get(1)),
                        List.of(userErrorMessages.get(7))),
                Arguments.of(LAST_NAME, "1", PHONE,
                        List.of(userLabelXPaths.get(1)),
                        List.of(userErrorMessages.get(8))),
                Arguments.of(LAST_NAME, "a".repeat(26), PHONE,
                        List.of(userLabelXPaths.get(1)),
                        List.of(userErrorMessages.get(9))),
                Arguments.of(LAST_NAME, FIRST_NAME, "",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(10))),
                Arguments.of(LAST_NAME, FIRST_NAME, " ",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(11))),
                Arguments.of(LAST_NAME, FIRST_NAME, "a",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(14))),
                Arguments.of(LAST_NAME, FIRST_NAME, "1",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(12))),
                Arguments.of(LAST_NAME, FIRST_NAME, "!",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(12))),
                Arguments.of(LAST_NAME, FIRST_NAME, "123456789",
                        List.of(userLabelXPaths.get(2)),
                        List.of(userErrorMessages.get(12)))
        );
    }
}
