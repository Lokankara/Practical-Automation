package com.softserve.edu.tests.example;

import com.softserve.edu.page.speak.ClubPage;
import com.softserve.edu.runner.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

class SimpleTest extends BaseTest {
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final By DREAM_TEAM = By.xpath("//div[contains(@class,'ant-card')]//div[contains(text(),'Dream Team')]");
    private static final By SEARCH_INPUT = By.cssSelector("input.ant-select-selection-search-input");
    private static final By CHANGE_BUTTON = By.cssSelector("div#demo > button");
    private static final By FIRST_TEXT_CSS = By.cssSelector("div#demo > h2");
    private static final By NEXT_P_CSS = By.cssSelector("div#demo > h1 + p");

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
    }

    @AfterAll
    public void afterAll() {
        closeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        deleteCookie("cookie");
        takeShot(testInfo);
        clearStorage();
        logger.warn(testInfo.getDisplayName());
    }

    @Test
    void checkGoogle() {
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        WebElement searchElement = driver.findElement(By.name("q"));
        searchElement.sendKeys("Selenium download");
        searchElement.submit();
        driver.findElement(By.xpath("//h3[text()='Install a Selenium library']")).click();
    }

    @Test
    void checkSpeakDreamTeam() {
        driver.get(BASE_URL);
        String dreamTeam = "Школа танців Dream Team";

        new ClubPage(driver)
                .searchClub("Школа")
                .assertPresentClubName(dreamTeam)
                .openModal(dreamTeam);
    }

    // Thread.sleep()
    // @Test
    void checkSpeak2() {
        driver.get(BASE_URL);
        WebElement searchElement = driver.findElement(SEARCH_INPUT);
        searchElement.sendKeys("Школа");
        WebElement searchClub = driver.findElement(DREAM_TEAM);
        logger.info("searchClub = " + searchClub.getText());
        Assertions.assertEquals("Школа танців Dream Team", searchClub.getText());
        searchClub.click();
    }

    // Implicit Waits
    // @Test
    void checkSpeak3() {
        // Setting timeout searching of web element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // 0 by default
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300)); // 300 by default
        //driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(30)); // 30 by default
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30)); // 30 by default
        driver.get(BASE_URL);
        WebElement searchElement = driver.findElement(SEARCH_INPUT);
        searchElement.sendKeys("Школа");
        WebElement searchClub = driver.findElement(DREAM_TEAM);
        logger.info("searchClub = " + searchClub.getText());
        Assertions.assertEquals("Школа танців Dream Team", searchClub.getText());
        searchClub.click();
    }

    // Explicit Waits
    // @Test
    void checkSpeak4() {
        driver.get(BASE_URL);
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchElement = driverWait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
        searchElement.sendKeys("Школа");
        WebElement searchClub = driverWait.until(ExpectedConditions.visibilityOfElementLocated(DREAM_TEAM));
        logger.info("searchClub = " + searchClub.getText());
        Assertions.assertEquals("Школа танців Dream Team", searchClub.getText());
        searchClub.click();
    }

    // Implicit/Explicit Waits. elementToBeClickable()
    //@Test
    void checkSpeak5() {
        driver.get(BASE_URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // 0 by default
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchElement = driver.findElement(SEARCH_INPUT);
        searchElement.sendKeys("Школа");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        WebElement searchClub = driverWait.until(ExpectedConditions.elementToBeClickable(DREAM_TEAM));
        logger.info("searchClub = " + searchClub.getText());
        Assertions.assertEquals("Школа танців Dream Team", searchClub.getText());
        searchClub.click();
    }

    // Custom Waits
    @Test
    void checkSpeak6() {
        driver.get(BASE_URL);
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driverWait.until((ExpectedCondition<Boolean>) driver -> Objects.requireNonNull(driver).getTitle().contains("Навчай українською"));
        WebElement searchElement = driverWait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
        searchElement.sendKeys("Школа");
        WebElement searchClub = driverWait.until(ExpectedConditions.elementToBeClickable(DREAM_TEAM));
        logger.info("searchClub = " + searchClub.getText());
        Assertions.assertEquals("Школа танців Dream Team", searchClub.getText());
        searchClub.click();
    }

    // FluentWait
    //@Test
    void checkSpeak7() {
        Wait<WebDriver> driverWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(NullPointerException.class)
                .ignoring(TimeoutException.class);
        driver.get(BASE_URL);
        driverWait.until((ExpectedCondition<Boolean>) driver -> Objects.requireNonNull(driver).getTitle().contains("Навчай українською"));
        WebElement searchElement = driverWait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
        searchElement.sendKeys("Школа");
    }

    // Ajax test
    //@Test
    void checkAjax1() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.w3schools.com/xml/ajax_intro.asp");
        WebElement firstText = driverWait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_TEXT_CSS));
        //        By.xpath("//div[@id='demo']/h2")));
        WebElement changeButton = driverWait.until(ExpectedConditions.presenceOfElementLocated(CHANGE_BUTTON));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].scrollIntoView(true);", firstText);
        new Actions(driver).moveToElement(changeButton).perform();
        changeButton.click();
        driverWait.until(ExpectedConditions.stalenessOf(firstText));
        //driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(FIRST_TEXT_CSS)));
        //driverWait.until(ExpectedConditions
        //        .invisibilityOfElementLocated(By.cssSelector(FIRST_TEXT_CSS)));
        //driverWait.until(ExpectedConditions
        //        .invisibilityOfElementWithText(By.cssSelector(FIRST_TEXT_CSS), "Let AJAX change this text")));
        WebElement nextParagraf = driverWait.until(ExpectedConditions.presenceOfElementLocated(NEXT_P_CSS));
        Assertions.assertEquals("AJAX is not a programming language.", nextParagraf.getText());

    }

    // Ajax2 test
//    @Test
    void checkAjax2() {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.w3schools.com/xml/ajax_intro.asp");
        WebElement firstText = driverWait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_TEXT_CSS));
        //        By.xpath("//div[@id='demo']/h2")));
        WebElement changeButton = driverWait.until(ExpectedConditions.presenceOfElementLocated(CHANGE_BUTTON));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].scrollIntoView(true);", firstText);
        Actions action = new Actions(driver);
        action.moveToElement(changeButton).perform();
        changeButton.click();
        //driverWait.until(ExpectedConditions.stalenessOf(firstText));
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(FIRST_TEXT_CSS));
        WebElement nextP = driverWait.until(ExpectedConditions.presenceOfElementLocated(NEXT_P_CSS));
        Assertions.assertEquals("AJAX is not a programming language.", nextP.getText());
    }
}
