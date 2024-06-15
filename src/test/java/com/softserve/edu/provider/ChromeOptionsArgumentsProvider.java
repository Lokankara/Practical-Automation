package com.softserve.edu.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.stream.Stream;

import static com.softserve.edu.runner.TestData.testArguments;

public class ChromeOptionsArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(buildChromeOptions(testArguments.get(0), testArguments.get(1), testArguments.get(2), testArguments.get(3))),
                Arguments.of(buildChromeOptions(testArguments.get(0), testArguments.get(1), testArguments.get(2), testArguments.get(4))),
                Arguments.of(buildChromeOptions(testArguments.get(0), testArguments.get(1), testArguments.get(5), testArguments.get(6))),
                Arguments.of(buildChromeOptions(testArguments.get(7))),
                Arguments.of(buildChromeOptions(testArguments.get(8)))

        );
    }

    private ChromeOptions buildChromeOptions(String... arguments) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(arguments);
        return options;
    }
}
