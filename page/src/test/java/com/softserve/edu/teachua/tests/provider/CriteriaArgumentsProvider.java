package com.softserve.edu.teachua.tests.provider;

import com.softserve.edu.teachua.entity.Criteria;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CriteriaArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new Criteria("Харків", "Новий Кадр")),
                Arguments.of(new Criteria("Дніпро", "Умнічка")),
                Arguments.of(new Criteria("Одеса", "Boteon"))
        );
    }
}
