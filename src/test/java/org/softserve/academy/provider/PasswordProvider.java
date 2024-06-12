package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

import static org.softserve.academy.provider.UserInfoData.CURRENT_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.NEW_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.WRONG_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.passwordErrorMessages;
import static org.softserve.academy.provider.UserInfoData.passwordLabelXPaths;

public class PasswordProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)  {
        return Stream.of(
                Arguments.of("", "", "",
                        passwordLabelXPaths,
                        List.of(passwordErrorMessages.get(0), passwordErrorMessages.get(1), passwordErrorMessages.get(3), passwordErrorMessages.get(4))),
                Arguments.of("", NEW_PASSWORD, "",
                        List.of(passwordLabelXPaths.get(0), passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(0), passwordErrorMessages.get(4))),
                Arguments.of("", "", NEW_PASSWORD,
                        List.of(passwordLabelXPaths.get(0), passwordLabelXPaths.get(1), passwordLabelXPaths.get(2), passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(0), passwordErrorMessages.get(1), passwordErrorMessages.get(3), passwordErrorMessages.get(6))),
                Arguments.of("", NEW_PASSWORD, NEW_PASSWORD,
                        List.of(passwordLabelXPaths.get(0)),
                        List.of(passwordErrorMessages.get(0))),
                Arguments.of("", NEW_PASSWORD, WRONG_PASSWORD,
                        List.of(passwordLabelXPaths.get(0), passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(0), passwordErrorMessages.get(6))),
                Arguments.of(CURRENT_PASSWORD, "", "",
                        List.of(passwordLabelXPaths.get(1), passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(1), passwordErrorMessages.get(4))),
                Arguments.of(CURRENT_PASSWORD, CURRENT_PASSWORD, "",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(3))),
                Arguments.of(CURRENT_PASSWORD, "", CURRENT_PASSWORD,
                        List.of(passwordLabelXPaths.get(1), passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(1), passwordErrorMessages.get(6))),
                Arguments.of(CURRENT_PASSWORD, "Password!", "Password!",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, CURRENT_PASSWORD, CURRENT_PASSWORD,
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(3))),
                Arguments.of(CURRENT_PASSWORD, "Password123", "Password123",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "WithoutNumbers!", "WithoutNumbers!",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "WithoutSpecial123", "WithoutSpecial123",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "WithoutSpecial", "WithoutSpecial",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "withoutuppercase", "withoutuppercase",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "WITHOUTLOWERCASE", "WITHOUTLOWERCASE",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "short", "short",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(5))),
                Arguments.of(CURRENT_PASSWORD, "1234567890", "1234567890",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(2))),
                Arguments.of(CURRENT_PASSWORD, "VeryLongPasswordThatExceeds20Characters", "VeryLongPasswordThatExceeds20Characters",
                        List.of(passwordLabelXPaths.get(1)),
                        List.of(passwordErrorMessages.get(5))),
                Arguments.of(CURRENT_PASSWORD, NEW_PASSWORD, WRONG_PASSWORD,
                        List.of(passwordLabelXPaths.get(3)),
                        List.of(passwordErrorMessages.get(6))));

//                Arguments.of("Gard3ner#", "password123!", "password123!", List.of(xpaths.get(1)), List.of(errorMessages.get(5))),
//                Arguments.of("Gard3ner#", "PASSWORD123!", "PASSWORD123!", List.of(xpaths.get(1)), List.of(errorMessages.get(5))),
    }
}
