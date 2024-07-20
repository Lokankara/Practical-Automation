package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class SearchExplicit extends Search {
    private final Long DEFAULT_EXPLICIT_WAIT_SECONDS = 10L;
    private static ThreadLocal<WebDriverWait> driverWaits = new ThreadLocal<>();

    public SearchExplicit() {
        DriverUtils.clearImplicitWait();
        initExplicit();
    }

    private void initExplicit() {
        long propertyWaitSeconds = DEFAULT_EXPLICIT_WAIT_SECONDS;
        String propertyWait = PropertiesUtils.get().readExplicitWait();
        try {
            propertyWaitSeconds = Long.parseLong(propertyWait);
        } catch (NumberFormatException e) {
            System.out.println("ERROR Reading Explicit properties. Use default: " + DEFAULT_EXPLICIT_WAIT_SECONDS + "  Message = " + e.getMessage());
        }
        WebDriverWait driverWait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(propertyWaitSeconds));
        driverWaits.set(driverWait);
        System.out.println("\tUsing SearchExplicit");
    }

    protected WebDriverWait getDriverWait() {
        WebDriverWait driverWait = driverWaits.get();
        if (driverWait == null) {
            initExplicit();
            driverWait = driverWaits.get();
        }
        return driverWait;
    }
}
