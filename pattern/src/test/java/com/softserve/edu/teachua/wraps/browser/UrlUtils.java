package com.softserve.edu.teachua.wraps.browser;

import com.softserve.edu.teachua.tools.PropertiesUtils;

public final class UrlUtils {
    private static final String DEFAULT_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static String baseUrl;

    static {
        initUrl();
    }

    private UrlUtils() {
        initUrl();
    }

    private static void initUrl() {
        baseUrl = PropertiesUtils.get().readBaseUrl();
        if (baseUrl.equals(PropertiesUtils.ERROR_READ_PROPERTY)) {
            baseUrl = DEFAULT_URL;
        }
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
