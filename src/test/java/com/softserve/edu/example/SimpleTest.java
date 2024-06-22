package com.softserve.edu.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleTest {
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private WebDriver driver;
    private JavascriptExecutor executor;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
        executor = (JavascriptExecutor) driver;
    }

    @AfterAll
    public void afterAll() {

        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    public void afterEach() {
        executor.executeScript("window.localStorage.clear()");
        executor.executeScript("window.sessionStorage.clear();");
    }

    @Test
    void checkAddComment() {
        openLoginModal();
        login();
        driver.findElement(By.cssSelector("div.login-footer button")).click();
        driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']")).click();
        driver.findElement(By.cssSelector("input.ant-select-selection-search-input")).sendKeys("IT освіта");
        Assertions.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'ant-card-body')]//div[contains(text(),'IT освіта: курси')]")).getText().contains("ГРАНД"));
        driver.findElement(By.xpath("//div[@class='ant-card-body']//div[contains(text(),'IT освіта: курси')]/../../a[contains(@href, '/club/')]")).click();
        driver.findElement(By.cssSelector("button.comment-button")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]"))).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.findElement(By.id("comment-edit_commentText")).click();
        driver.findElement(By.id("comment-edit_commentText")).clear();
        driver.findElement(By.id("comment-edit_commentText")).sendKeys("Проба Коментар");
        driver.findElement(By.cssSelector("button.do-comment-button")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebElement comment = new WebDriverWait(driver, Duration.ofSeconds(5L))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ant-comment-content-detail']/p)[position()=1]")));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Assertions.assertEquals("Проба Коментар", comment.getText());
        logOut();
    }

    private void login() {
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys("yagifij495@eqvox.com");
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("Qwerty_1");
    }

    @Test
    void checkLogin() {
        openLoginModal();
        login();
        driver.findElement(By.cssSelector("div.login-footer button")).click();
        // TODO Check message        // Search web element
        WebElement cityName = driver.findElement(By.cssSelector("h2.city-name"));
        Assertions.assertTrue(cityName.getText().contains("Навчай українською"));
        logOut();
    }

    private void openLoginModal() {
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        driver.findElement(By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content")).click();
    }

    private void logOut() {
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5L)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-menu-id*='logout'] span.ant-dropdown-menu-title-content"))).click();
    }

    @Test
    void checkClubExist() {
        driver.findElement(By.cssSelector("input.ant-select-selection-search-input")).sendKeys("Школа");
        WebElement titleClub = driver.findElement(By.xpath("//div[contains(@class,'ant-card')]//div[contains(text(),'Dream Team')]"));
        Assertions.assertEquals("Школа танців Dream Team", titleClub.getText());
        titleClub.click();
    }
}
