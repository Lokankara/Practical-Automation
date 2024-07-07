package com.softserve.edu.factory;

import java.util.Arrays;

public enum Browsers {
    DEFAULT,
    CHROME,
    EDGE,
    SAFARI,
    FIREFOX;

    public static Browsers fromString(String value) {
        return Arrays.stream(Browsers.values())
                .filter(browser -> browser.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(DEFAULT);
    }
}
