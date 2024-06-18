package com.softserve.edu.tests.example;

import com.softserve.edu.runner.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
    public void beforeEach() {
        driver.get(BASE_URL);
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_FIVE_SECONDS));
        List<WebElement> footerButton = driver.findElements(By.xpath("//footer[contains(@class,'cookie')]//button"));
        logger.info("footerButton.size() = " + footerButton.size());
        if (!footerButton.isEmpty()) {
            footerButton.get(0).click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }

    //@Test
    public void checkPage() {
        String expectedText = "Use Paging with Other Data Processing Plugins";
        WebElement position = driver.findElement(PAGING);
        Assertions.assertTrue(position.getText().contains(expectedText));
    }

    // Wrong Test
    //@Test
    public void checkIframeAjaxWrong() {
        switchToPosition();
        findFirst();
        removeRow();
        assertNevadaSecondData();
    }

    // Thread Sleep
    //@Test
    public void checkIframeAjaxThreadSleep() {
        switchToPosition();
        findFirst();
        removeRow();
        assertNevadaSecondData();
    }

    @Test
    @DisplayName("Explicit Waits")
    void checkIframeAjaxExplicit() {
        switchToPosition();
        findFirst();
        String removeText = removeRow();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        //(new WebDriverWait(driver, Duration.ofSeconds(10)))
        //        .until(ExpectedConditions.stalenessOf(tdNevadaFirstData));
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//td[text()='" + removeText + "']")));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        assertNevadaSecondData();
    }

    private void assertNevadaSecondData() {
        WebElement tdNevadaSecondData = driver.findElement(NEVADA_SIBLING_FIRST);
        logger.info("tdNevadaSecondData.getText() = " + tdNevadaSecondData.getText());
        Assertions.assertEquals("2013/12/07", tdNevadaSecondData.getText());
    }

    private void switchToPosition() {
        WebElement position = driver.findElement(PAGING);
        Actions action = new Actions(driver);
        action.moveToElement(position).perform();
        driver.switchTo().frame(driver.findElement(NEVADA_IFRAME));
    }

    private String removeRow() {
        String removeText = driver.findElement(REMOVE_TEXT).getText();
        logger.info("removeText = " + removeText);
        driver.findElement(REMOVE_BUTTON).click();
        return removeText;
    }

    private void findFirst() {
        WebElement tdNevadaFirstData = driver.findElement(NEVADA_SIBLING_FIRST);
        logger.info("tdNevadaFirstData.getText() = " + tdNevadaFirstData.getText());
    }
}
