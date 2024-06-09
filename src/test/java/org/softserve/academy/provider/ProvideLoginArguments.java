package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProvideLoginArguments implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext context) {
        List<String> emails = readCsvFile("/emails.csv");
        List<String> passwords = readCsvFile("/valid_passwords.csv");
        return IntStream.range(0, Math.min(passwords.size(), emails.size()))
                .mapToObj(i -> Arguments.of(emails.get(i), passwords.get(i)));
    }

    private List<String> readCsvFile(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }
}