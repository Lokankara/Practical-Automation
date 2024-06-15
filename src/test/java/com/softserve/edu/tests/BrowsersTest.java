package com.softserve.edu.tests;

import com.softserve.edu.manager.Browsers;
import com.softserve.edu.provider.ChromeOptionsArgumentsProvider;
import com.softserve.edu.provider.EdgeOptionsArgumentsProvider;
import com.softserve.edu.provider.FirefoxOptionsProvider;
import com.softserve.edu.reporter.LoggerUtils;
import com.softserve.edu.runner.BaseBrowserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

import static com.softserve.edu.manager.Browsers.CHROME;
import static com.softserve.edu.manager.Browsers.FIREFOX;
import static com.softserve.edu.runner.TestData.DEFAULT_URL;
import static com.softserve.edu.runner.TestData.SPEAK_UKRAINIAN_URL;
import static com.softserve.edu.runner.TestData.testArguments;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class BrowsersTest extends BaseBrowserTest {

    @ParameterizedTest
    @DisplayName("Test opening Google in different browsers")
    @EnumSource(value = Browsers.class, names = {"CHROME", "EDGE", "FIREFOX"})
    void testDifferentBrowsers(Browsers browser) {
        WebDriver driver = getWebDriver(browser);
        driver.get(DEFAULT_URL);

        assertContainsUrl(driver.getCurrentUrl(), DEFAULT_URL);
    }

    @ParameterizedTest
    @DisplayName("Test FirefoxDriver with fill search submit")
    @ValueSource(strings = {"Cheese!", "Cake"})
    void testFirefoxWithFillSearchSubmit(String value) {
        WebDriver driver = getWebDriver(FIREFOX);
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, value);

        assertContainsUrl(driver.getCurrentUrl(), DEFAULT_URL);
        takeScreenShot(driver);
    }

    @ParameterizedTest(name = "Test FirefoxDriver options{index}: {0}")
    @ArgumentsSource(FirefoxOptionsProvider.class)
    @DisplayName("Test Driver with Firefox's options")
    void testFirefoxWithOptionsMode(FirefoxOptions options) {
        String value = "Cheese";
        WebDriver driver = getWebDriver(options);
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, value);
        assertContainsUrl(driver.getCurrentUrl(), DEFAULT_URL);
    }

    @ParameterizedTest
    @ArgumentsSource(EdgeOptionsArgumentsProvider.class)
    @DisplayName("Test EdgeDriver with different configurations")
    void testEdgeDriver(EdgeOptions options) {
        WebDriver driver = getWebDriver(options);
        driver.get(SPEAK_UKRAINIAN_URL);

        assertEquals(SPEAK_UKRAINIAN_URL, driver.getCurrentUrl(), "URL should be the" + SPEAK_UKRAINIAN_URL);
        takeScreenShot(driver);
    }

    @ParameterizedTest
    @DisplayName("Test ChromeDriver")
    @ValueSource(strings = {"Cheese", "Cake"})
    void testChrome(String value) {
        final String expected = "Google";
        WebDriver driver = getWebDriver(CHROME);
        driver.manage().window().maximize();
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, value);

        assertTitle(driver.getTitle(), expected);
        assertTrue(driver.getCurrentUrl().contains(DEFAULT_URL));
        takeScreenShot(driver);
    }

    @ParameterizedTest
    @ArgumentsSource(ChromeOptionsArgumentsProvider.class)
    @DisplayName("Test ChromeDriver with different Options configurations")
    void testChromeDriver(ChromeOptions options) {
        final String expected = "Google";
        final String value = "Cheese";

        WebDriver driver = getWebDriver(options);
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, value);

        assertTitle(driver.getTitle(), expected);
        assertContainsUrl(driver.getCurrentUrl(), DEFAULT_URL);
        takeScreenShot(driver);
    }

    @Test
    @DisplayName("Test ChromeDriver ignore certificate errors")
    void testChromeWithIgnoreCertificate() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(testArguments.subList(0, 5));
        WebDriver driver = getWebDriver(options);
        driver.get(SPEAK_UKRAINIAN_URL);

        assertEquals(SPEAK_UKRAINIAN_URL, driver.getCurrentUrl(), "URL should be the URL: " + SPEAK_UKRAINIAN_URL);
        takeScreenShot(driver);
    }


    // @Test Specified location
    void testChromeSpecifiedLocation() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-proxy-server");
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        WebDriver driver = getWebDriver(options);
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, "Cheese!");

        assertContainsUrl(driver.getCurrentUrl(), DEFAULT_URL);
        takeScreenShot(driver);
    }

    @Test
    @DisplayName("Test HtmlUnit")
    void testHtmlUnit() {
        WebDriver driver = new HtmlUnitDriver(false);
        //((HtmlUnitDriver) driver).setJavascriptEnabled(true); // TODO Enable CSS
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.get(DEFAULT_URL);
        LoggerUtils.logInfo("driver.get http://www.google.com DONE", "Current title is: ", driver.getTitle());
        fillSearchSubmit(driver, "Cheese!");

        LoggerUtils.logInfo("element.sendKeys Cheese! DONE", "Page title is", driver.getTitle());
        driver.findElement(By.partialLinkText("Cheese - Wikipedia")).click();
        LoggerUtils.logInfo("ELEMENT IS ", driver.findElement(By.id("firstHeading")).getText());
        LoggerUtils.logInfo("Page title is", driver.getTitle());
        assertEquals("Cheese", driver.findElement(By.id("firstHeading")).getText());
    }


    // @Test Use Selenium 4.7.1
    void testPhantomjs() {
        //System.setProperty("phantomjs.binary.path", "./lib/phantomjs.exe");
        // WebDriverManager.phantomjs().setup();
        WebDriver driver = new PhantomJSDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(DEFAULT_URL);
        LoggerUtils.logInfo("driver.get https://www.google.com DONE", "Current title is: ", driver.getTitle());
        fillSearchSubmit(driver, "Cheese!");
        LoggerUtils.logInfo("Page title is", driver.getTitle(), "Taking First Screenshot ...");

        takeScreenShot(driver);
        LoggerUtils.logInfo("Done Screenshot");
    }

//    @BeforeAll
//    public static void createService() throws Exception {
//        service = new ChromeDriverService.Builder()
//                // .usingDriverExecutable(new File("./lib/chromedriver.exe"))
//                .usingDriverExecutable(new File(System.getenv("HOMEPATH")
//                        + "\\.cache\\selenium\\chromedriver\\win64\\125.0.6422.141\\chromedriver.exe"))
//                // .usingAnyFreePort()
//                .usingPort(8888).build();
//        service.start();
//        LoggerUtils.logInfo("+++Service Start");
//    }

    //@Test
    void testRemoute() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-proxy-server");
        //options.addArguments("--no-sandbox");
        //options.addArguments("--disable-web-security");
        //options.addArguments("--ignore-certificate-errors");
        //options.addArguments("--disable-extensions");
        // WebDriver driver = new ChromeDriver(options);
        //
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        // DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        // WebDriver driver = new RemoteWebDriver(new URL("127.0.0.1:8888"),capabilities);
        WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
        LoggerUtils.logInfo("+++RemoteWebDriver Start, service.getUrl()=" + service.getUrl());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(DEFAULT_URL);
        fillSearchSubmit(driver, "Cheese!");

        takeScreenShot(driver);
        driver.quit();
        LoggerUtils.logInfo("+++driver.quit()");
    }
}
