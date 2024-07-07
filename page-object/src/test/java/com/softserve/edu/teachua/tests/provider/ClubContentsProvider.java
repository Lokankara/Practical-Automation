package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.data.ClubContents;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ClubContentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(ClubContents.values()).map(Arguments::of);
    }
}
