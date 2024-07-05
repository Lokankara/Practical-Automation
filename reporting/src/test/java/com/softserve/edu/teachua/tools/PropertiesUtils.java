package com.softserve.edu.teachua.tools;

import java.io.FileInputStream;
import java.util.Properties;

enum ApplicationProperties {
    BASE_URL("base.url"),
    BROWSER_NAME("browser.name"),
    SEARCH_STRATEGY("search.strategy"),
    IMPLICIT_WAIT_SECONDS("implicit.wait.seconds"),
    EXPLICIT_WAIT_SECONDS("explicit.wait.seconds"),
    DATASOURCE_DRIVER_NAME("datasource.driver-class-name"),
    DATASOURCE_URL("datasource.url"),
    DATASOURCE_USERNAME("datasource.username"),
    DATASOURCE_PASSWORD("datasource.password");

    private String propertyName;

    private ApplicationProperties(String propertyName) {
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

public class PropertiesUtils {
    public static final String ERROR_READ_PROPERTY = "Error, property not found";
    private static final String DEFAULT_FILENAME = "application-test.properties";
    private static final String PATH_SEPARATOR = "/";
    private static volatile PropertiesUtils instance = null;
    private Properties appProps;
    private String filename;

    private PropertiesUtils(String filename) {
        this.filename = filename;
        init();
    }

    public static PropertiesUtils get() {
        return get(DEFAULT_FILENAME);
    }

    public static PropertiesUtils get(String filename) {
        if (instance == null) {
            synchronized (PropertiesUtils.class) {
                if (instance == null) {
                    instance = new PropertiesUtils(filename);
                }
            }
        }
        return instance;
    }

    private void init() {
        appProps = new Properties();
        String filePath = getFullPath();
        loadProperties(filePath);
    }

    private String getFullPath() {
        return this.getClass().getResource(PATH_SEPARATOR + filename).getPath();
    }

    private void loadProperties(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            appProps.load(fileInputStream);
        } catch (Exception e) {
            ReportUtils.logError("Error reading " + filePath + ". Message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String readProperty(String propertyName) {
        return appProps.getProperty(propertyName, ERROR_READ_PROPERTY);
    }

    public String readBaseUrl() {
        return readProperty(ApplicationProperties.BASE_URL.getPropertyName());
    }

    public String readBrowserName() {
        return readProperty(ApplicationProperties.BROWSER_NAME.getPropertyName());
    }

    public String readSearchStrategy() {
        return readProperty(ApplicationProperties.SEARCH_STRATEGY.getPropertyName());
    }

    public String readImplicitWait() {
        return readProperty(ApplicationProperties.IMPLICIT_WAIT_SECONDS.getPropertyName());
    }

    public String readExplicitWait() {
        return readProperty(ApplicationProperties.EXPLICIT_WAIT_SECONDS.getPropertyName());
    }

    public String readDatasourceDriverName() {
        return readProperty(ApplicationProperties.DATASOURCE_DRIVER_NAME.getPropertyName());
    }

    public String readDatasourceUrl() {
        return readProperty(ApplicationProperties.DATASOURCE_URL.getPropertyName());
    }

    public String readDatasourceUsername() {
        return readProperty(ApplicationProperties.DATASOURCE_USERNAME.getPropertyName());
    }

    public String readDatasourcePassword() {
        return readProperty(ApplicationProperties.DATASOURCE_PASSWORD.getPropertyName());
    }
}
