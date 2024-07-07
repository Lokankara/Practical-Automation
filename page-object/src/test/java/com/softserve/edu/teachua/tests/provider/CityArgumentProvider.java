package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.data.Cities;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CityArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Cities.DEFAULT_CITY,
                Cities.KYIV_CITY,
                Cities.HARKIV_CITY,
                Cities.DNIPRO_CITY,
                Cities.ODESA_CITY
        ).map(Arguments::of);
    }
}
