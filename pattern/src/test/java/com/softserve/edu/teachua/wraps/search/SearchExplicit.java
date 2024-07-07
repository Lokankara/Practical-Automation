package com.softserve.edu.teachua.wraps.search;

import com.softserve.edu.teachua.exceptions.InvalidPropertyException;
import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public abstract class SearchExplicit extends Search {
    private static final Long DEFAULT_EXPLICIT_WAIT_SECONDS = 10L;
    private static final ThreadLocal<WebDriverWait> driverWaits = new ThreadLocal<>();

    public SearchExplicit() {
        DriverUtils.getInstance().clearImplicitWait();
        initExplicit();
    }

    private void initExplicit() {
        long propertyWaitSeconds = DEFAULT_EXPLICIT_WAIT_SECONDS;
        try {
            propertyWaitSeconds = Long.parseLong(PropertiesUtils.get().readExplicitWait());
        } catch (NumberFormatException e) {
            logger.error(String.format("ERROR Reading Explicit properties. Use default: %d. Message = %s", DEFAULT_EXPLICIT_WAIT_SECONDS, e.getMessage()));
            throw new InvalidPropertyException("Invalid explicit wait property value", e);
        } finally {
            driverWaits.set(createWebDriverWait(propertyWaitSeconds));
            logger.info("Using SearchExplicit");
        }
    }

    protected WebDriverWait getDriverWait() {
        WebDriverWait driverWait = driverWaits.get();
        if (driverWait == null) {
            initExplicit();
            driverWait = driverWaits.get();
        }
        return driverWait;
    }

    protected <T> T applyWait(Function<WebDriverWait, T> condition) {
        clearImplicitlyWait();
        T result = condition.apply(getDriverWait());
        addImplicitlyWait();
        return result;
    }

    private WebDriverWait createWebDriverWait(long seconds) {
        return new WebDriverWait(DriverUtils.getInstance().getDriver(), Duration.ofSeconds(seconds));
    }

    private void addImplicitlyWait() {
        DriverUtils.getInstance().getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_EXPLICIT_WAIT_SECONDS));
    }

    private void clearImplicitlyWait() {
        DriverUtils.getInstance().getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
    }
}
