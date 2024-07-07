package com.softserve.edu.teachua.tools;

public enum ApplicationProperties {
    BASE_URL("base.url"),
    BROWSER_NAME("browser.name"),
    SEARCH_STRATEGY("search.strategy"),
    IMPLICIT_WAIT_SECONDS("implicit.wait.seconds"),
    EXPLICIT_WAIT_SECONDS("explicit.wait.seconds"),
    DATASOURCE_DRIVER_NAME("datasource.driver-class-name"),
    DATASOURCE_URL("datasource.url"),
    DATASOURCE_USERNAME("datasource.username"),
    DATASOURCE_PASSWORD("datasource.password"),
    USER_SOURCE("user.source"),
    SOURCE_CSV("source.csv-file"),
    SOURCE_EXCEL("source.excel-file");
    private final String propertyName;

    ApplicationProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
