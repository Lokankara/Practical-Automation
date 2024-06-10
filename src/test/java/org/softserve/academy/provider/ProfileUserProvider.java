package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.softserve.academy.runner.Helper;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProfileUserProvider implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext context) {
        List<String> user = Helper.getInstance().readCsvFile("/edit-profile-user.csv");
        List<String> password = Helper.getInstance().readCsvFile("/passwords.csv");

        return IntStream.range(1, Math.min(password.size(), user.size()))
                .mapToObj(i -> {
                    String[] userData = user.get(i).split(",");
                    String[] passwordData = password.get(i).split(",");
                    return Arguments.of(passwordData[0], passwordData[1], passwordData[2], userData[0], userData[1], userData[2]);
                });
    }
}
