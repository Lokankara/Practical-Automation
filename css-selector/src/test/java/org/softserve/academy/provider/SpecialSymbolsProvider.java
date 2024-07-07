package org.softserve.academy.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class SpecialSymbolsProvider implements ArgumentsProvider {

    private static final Set<Character> PROHIBITED_CHARACTERS = new HashSet<>(Arrays.asList(
            Keys.ALT.charAt(0), Keys.ARROW_LEFT.charAt(0), Keys.ARROW_UP.charAt(0),
            Keys.NULL.charAt(0), Keys.TAB.charAt(0), Keys.END.charAt(0),
            Keys.ESCAPE.charAt(0), Keys.DELETE.charAt(0), Keys.DOWN.charAt(0),
            Keys.CONTROL.charAt(0), Keys.PAGE_DOWN.charAt(0), Keys.PAUSE.charAt(0)
    ));

    private static final Set<Character> ALLOWS_CHARACTERS = new HashSet<>(Arrays.asList(
            '~', '@', '#', '?', '<', '>', '^', '?', '.', '%', '&',
            '*', '_', '-', '$', ' ', '\n', '\\', '\'', '`', '/'
    ));

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Stream<Arguments> allowedArguments = ALLOWS_CHARACTERS.stream().map(ch -> Arguments.of(repeatCharacter(11, ch), true));
        Stream<Arguments> prohibitedArguments = PROHIBITED_CHARACTERS.stream().map(ch -> Arguments.of(repeatCharacter(10, ch), false));
        return Stream.concat(allowedArguments, prohibitedArguments);
    }

    private String repeatCharacter(int length, char character) {
        char[] charArray = new char[length];
        Arrays.fill(charArray, character);
        return new String(charArray);
    }
}
