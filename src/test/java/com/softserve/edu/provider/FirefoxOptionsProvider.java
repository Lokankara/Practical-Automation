package com.softserve.edu.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.util.stream.Stream;

public class FirefoxOptionsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        FirefoxOptions headlessOptions = new FirefoxOptions().addArguments("-headless");
        FirefoxOptions defaultRelease = new FirefoxOptions().setProfile(new ProfilesIni().getProfile("default-release"));

//        String pathProfile = System.getProperty("user.home") + "/AppData/Roaming/Mozilla/Firefox/Profiles/ejwel82b.default-release";
//        FirefoxOptions options = new FirefoxOptions().setProfile(new FirefoxProfile(new File(pathProfile)));

        FirefoxOptions untrustedCertsOptions = new FirefoxOptions();
        untrustedCertsOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        FirefoxOptions untrustedCerts = new FirefoxOptions();
        untrustedCerts.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        return Stream.of(
//                Arguments.of(options),
                Arguments.of(defaultRelease),
                Arguments.of(headlessOptions),
                Arguments.of(untrustedCerts)
//                Arguments.of(untrustedCertsOptions)
        );
    }
}