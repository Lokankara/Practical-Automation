package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.softserve.academy.provider.UserInfoData.CURRENT_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.FIRST_NAME;
import static org.softserve.academy.provider.UserInfoData.LAST_NAME;
import static org.softserve.academy.provider.UserInfoData.MAX_LENGTH;
import static org.softserve.academy.provider.UserInfoData.MIN_LENGTH;
import static org.softserve.academy.provider.UserInfoData.NEW_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.PHONE;
import static org.softserve.academy.provider.UserInfoData.WRONG_PASSWORD;
import static org.softserve.academy.provider.UserInfoData.passwordErrorMessages;
import static org.softserve.academy.provider.UserInfoData.passwordLabelXPaths;
import static org.softserve.academy.provider.UserInfoData.userErrorMessages;
import static org.softserve.academy.provider.UserInfoData.userLabelXPaths;

public class InValidProfileUserProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(

                Arguments.of(LAST_NAME, "", "", "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", "", "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, "", "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, CURRENT_PASSWORD, "", "",
                        Collections.singletonList(userErrorMessages.get(5)),
                        Collections.singletonList(userLabelXPaths.get(1))
                ),
                Arguments.of(LAST_NAME, "", PHONE, "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of("", "", PHONE, "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),
                //Triple
                Arguments.of("", "", "", CURRENT_PASSWORD, NEW_PASSWORD, NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", CURRENT_PASSWORD, NEW_PASSWORD, LAST_NAME,
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2)
                        )
                ),
                Arguments.of("", "", PHONE, CURRENT_PASSWORD, NEW_PASSWORD, LAST_NAME,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, CURRENT_PASSWORD, NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, CURRENT_PASSWORD, "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of(LAST_NAME, FIRST_NAME, "", "", NEW_PASSWORD, NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, "", NEW_PASSWORD, NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, "", "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", CURRENT_PASSWORD, NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, CURRENT_PASSWORD, NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", CURRENT_PASSWORD, "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of("", "", PHONE, CURRENT_PASSWORD, "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", "", NEW_PASSWORD, NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of("", "", PHONE, "", NEW_PASSWORD, NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, CURRENT_PASSWORD, NEW_PASSWORD, NEW_PASSWORD,
                        Collections.singletonList(userErrorMessages.get(5)),
                        Collections.singletonList(userLabelXPaths.get(1))
                ),
                Arguments.of(LAST_NAME, FIRST_NAME, "", CURRENT_PASSWORD, NEW_PASSWORD, NEW_PASSWORD,
                        Collections.singletonList(userErrorMessages.get(10)),
                        Collections.singletonList(userLabelXPaths.get(2)
                        )
                ),
                // Empty CURRENT_PASSWORD
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, "", NEW_PASSWORD, NEW_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(0)),
                        Collections.singletonList(passwordLabelXPaths.get(0))
                ),
                // Empty NEW_PASSWORD(1)(25)
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "", NEW_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(1)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // Empty NEW_PASSWORD(2)
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, " ", NEW_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(2))
                ),
                // NEW_PASSWORD(1) azAZ/p
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "newPASSWORD!@#", "newPASSWORD!@#",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) azAZ/d
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "newPASSWORD123", "newPASSWORD123",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) az/d/p
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "12345678!@#", "12345678!@#",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
//                // NEW_PASSWORD(1) AZ/p/d (BUG)
//                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "PASSWORD!123", "PASSWORD!123",
//                        Arrays.asList(passwordErrorMessages.get(2), passwordErrorMessages.get(1)),
//                        Arrays.asList(passwordLabelXPaths.get(1), passwordLabelXPaths.get(2))
//                ),
                // NEW_PASSWORD(1) az/p
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "password!", "password!",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) azAZ
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "NewPassword", "NewPassword",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) AZ/p
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "PASSWORD!", "PASSWORD!",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) AZ/d
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "123PASSWORD", "123PASSWORD",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) /d/p
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, "12345678!", "12345678!",
                        Collections.singletonList(passwordErrorMessages.get(2)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) != NEW_PASSWORD(2)
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, NEW_PASSWORD, WRONG_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(6)),
                        Collections.singletonList(passwordLabelXPaths.get(3))
                ),
                // NEW_PASSWORD(1) = CURRENT_PASSWORD
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, CURRENT_PASSWORD, NEW_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(3)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) = CURRENT_PASSWORD, NEW_PASSWORD(1) != NEW_PASSWORD(2)
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, CURRENT_PASSWORD, WRONG_PASSWORD,
                        Collections.singletonList(passwordErrorMessages.get(3)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) len()<8
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, MIN_LENGTH, MIN_LENGTH,
                        Collections.singletonList(passwordErrorMessages.get(5)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                // NEW_PASSWORD(1) len()> 20
                Arguments.of(LAST_NAME, FIRST_NAME, PHONE, CURRENT_PASSWORD, MAX_LENGTH, MAX_LENGTH,
                        Collections.singletonList(passwordErrorMessages.get(5)),
                        Collections.singletonList(passwordLabelXPaths.get(1))
                ),
                Arguments.of("", "", "", "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", "", CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", "", "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", "", "", "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2)
                        )
                ),
                Arguments.of("", FIRST_NAME, "", "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", "", CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", "", "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),

                Arguments.of("", "", "", NEW_PASSWORD, "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0)
                        )
                ),
                Arguments.of(LAST_NAME, FIRST_NAME, "", "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", FIRST_NAME, "", CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, "", NEW_PASSWORD, "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, "", "", LAST_NAME,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1)
                        )
                ),
                Arguments.of("", FIRST_NAME, "", "", "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", PHONE, "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", FIRST_NAME, PHONE, "", "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                passwordErrorMessages.get(0),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(3),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                passwordLabelXPaths.get(0),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),

                Arguments.of("", "", "", CURRENT_PASSWORD, "", NEW_PASSWORD,
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(6)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of(LAST_NAME, "", "", CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(5),
                                userErrorMessages.get(10),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(1),
                                userLabelXPaths.get(2),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                ),
                Arguments.of("", "", PHONE, CURRENT_PASSWORD, "", "",
                        Arrays.asList(
                                userErrorMessages.get(0),
                                userErrorMessages.get(5),
                                passwordErrorMessages.get(1),
                                passwordErrorMessages.get(4)
                        ),
                        Arrays.asList(
                                userLabelXPaths.get(0),
                                userLabelXPaths.get(1),
                                passwordLabelXPaths.get(1),
                                passwordLabelXPaths.get(3)
                        )
                )
        );
    }
}
