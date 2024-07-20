package com.softserve.edu.teachua.service.selenium;

import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.browser.UrlUtils;

public class SeleniumService {

    protected HomePage loadApplication() {
        DriverUtils.getUrl(UrlUtils.getBaseUrl());
        return new HomePage();
    }
}
