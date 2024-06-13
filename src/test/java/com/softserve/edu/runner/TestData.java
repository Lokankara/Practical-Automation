package com.softserve.edu.runner;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.manager.Configuration;

public class TestData {

    public static final Browsers BROWSER = Browsers.valueOf(Configuration.getInstance().getBrowser().toUpperCase());
    public static final String BASE_URL = Configuration.getInstance().getBaseURL();
}
