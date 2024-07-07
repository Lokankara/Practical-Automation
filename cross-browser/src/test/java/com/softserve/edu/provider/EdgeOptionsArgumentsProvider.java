package com.softserve.edu.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.stream.Stream;

public class EdgeOptionsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        EdgeOptions insecure = new EdgeOptions();
        insecure.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return Stream.of(
                Arguments.of(insecure),
                Arguments.of(new EdgeOptions().addArguments("-headless"))
        );
    }
}
