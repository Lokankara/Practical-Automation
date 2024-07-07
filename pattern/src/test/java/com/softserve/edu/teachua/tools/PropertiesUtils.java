package com.softserve.edu.teachua.tools;

import com.softserve.edu.teachua.exceptions.IOCustomException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesUtils {

    protected static final Logger logger = Logger.getLogger(PropertiesUtils.class.getName());
    public static final String ERROR_READ_PROPERTY = "Error, property not found";
    private static final String DEFAULT_FILENAME = "application-test.properties";
    private static final String PATH_SEPARATOR = "/";
    private static volatile PropertiesUtils instance = null;
    private Properties appProps;
    private final String filename;

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
        loadProperties(getFullPath());
    }

    private String getFullPath() {
        return Objects.requireNonNull(this.getClass().getResource(PATH_SEPARATOR + filename)).getPath();
    }

    private void loadProperties(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            appProps.load(fileInputStream);
        } catch (Exception e) {
            logger.error(String.format("ERROR Reading %s  Message = %s", filePath, e.getMessage()));
            throw new IOCustomException(e.getMessage());
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

    public String readUserSource() {
        return readProperty(ApplicationProperties.USER_SOURCE.getPropertyName());
    }

    public String readSourceCsv() {
        return readProperty(ApplicationProperties.SOURCE_CSV.getPropertyName());
    }

    public String readSourceExcel() {
        return readProperty(ApplicationProperties.SOURCE_EXCEL.getPropertyName());
    }
}
