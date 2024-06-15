package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.softserve.academy.runner.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LoginProvider implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext context) {
        List<String> emails = Helper.getInstance().readCsvFile("/emails.csv");
        List<String> passwords = Helper.getInstance().readCsvFile("/valid_passwords.csv");
        return IntStream.range(0, Math.min(passwords.size(), emails.size()))
                .mapToObj(i -> Arguments.of(emails.get(i), passwords.get(i)));
    }
}
