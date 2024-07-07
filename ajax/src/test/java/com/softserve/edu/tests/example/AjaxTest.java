package com.softserve.edu.tests.example;

import com.softserve.edu.runner.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

class AjaxTest extends BaseTest {

    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final Long IMPLICITLY_WAIT_FIVE_SECONDS = 5L;
    private static final By REMOVE_BUTTON = By.xpath("//button[text()='2']");
    private static final By PAGING = By.id("use-paging-with-other-data-processing-plugins");
    private static final By REMOVE_TEXT = By.xpath("//td[text()='Nevada']/preceding-sibling::td[3]");
    private static final By NEVADA_SIBLING_FIRST = By.xpath("//td[text()='Nevada']/preceding-sibling::td[2]");
    private static final By NEVADA_IFRAME = By.xpath("//div[contains(@data-options,'remote-paging')]//iframe");
    private static final String BASE_URL = "https://devexpress.github.io/devextreme-reactive/react/grid/docs/guides/paging/";

    @AfterAll
    public void afterAll() {
        closeDriver();
    }

    @BeforeEach
    void beforeEach() {
        getDriver().get(BASE_URL);
        closePopup();
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        deleteCookie("cookie");
        takeShot(testInfo);
        clearStorage();
        logger.info(testInfo.getDisplayName());
    }


    private void closePopup() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_FIVE_SECONDS));
        List<WebElement> footerButton = getDriver().findElements(By.xpath("//footer[contains(@class,'cookie')]//button"));
        logger.info("footerButton.size() = {}", footerButton.size());
        if (!footerButton.isEmpty()) {
            footerButton.get(0).click();
        }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }

    @Test
    void checkPage() {
        String expectedText = "Use Paging with Other Data Processing Plugins";
        WebElement position = getDriver().findElement(PAGING);
        Assertions.assertTrue(position.getText().contains(expectedText));
    }

    @Test
    void checkIframeAjaxThreadSleep() {
        switchToPosition();
        findFirst();
        removeRow();
        assertNevadaSecondData("2013/11/12");
    }

    @Test
    @DisplayName("Explicit Waits")
    void checkIframeAjaxExplicit() {
        switchToPosition();
        findFirst();
        String removeText = removeRow();
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        //(new WebDriverWait(driver, Duration.ofSeconds(10)))
        //        .until(ExpectedConditions.stalenessOf(tdNevadaFirstData));
        (new WebDriverWait(getDriver(), Duration.ofSeconds(10))).until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//td[text()='" + removeText + "']")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertNevadaSecondData("2013/12/07");
    }

    private void assertNevadaSecondData(String expected) {
        WebElement tdNevadaSecondData = getDriver().findElement(NEVADA_SIBLING_FIRST);
        logger.info("tdNevadaSecondData.getText() = {}", tdNevadaSecondData.getText());
        Assertions.assertEquals(expected, tdNevadaSecondData.getText());
    }

    private void switchToPosition() {
        WebElement position = getDriver().findElement(PAGING);
        Actions action = new Actions(getDriver());
        action.moveToElement(position).perform();
        getDriver().switchTo().frame(getDriver().findElement(NEVADA_IFRAME));
    }

    private String removeRow() {
        String removeText = getDriver().findElement(REMOVE_TEXT).getText();
        getDriver().findElement(REMOVE_BUTTON).click();
        logger.info("removeText = {}", removeText);

        return removeText;
    }

    private void findFirst() {
        WebElement tdNevadaFirstData = getDriver().findElement(NEVADA_SIBLING_FIRST);
        logger.info("tdNevadaFirstData.getText() = {}", tdNevadaFirstData.getText());
    }
}
