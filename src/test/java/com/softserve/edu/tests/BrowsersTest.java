package com.softserve.edu.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BrowsersTest {
    //        var headless = Configuration.getInstance().isHeadless();
//        var implicitWait = Configuration.getInstance().getImplicitWait();
//        driver = (gridUrl != null && !gridUrl.isBlank())
//                ? DriverFactoryBuilder.getFactory(browser, gridUrl).getDriver()
//                : DriverFactoryBuilder.getFactory(browser).setHeadless(headless).getDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
//        driver.manage().window().maximize();
//        driver.navigate().to(Configuration.getInstance().getEnvironment());
    private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    //    public static ChromeDriverService service;
    public static DriverService service;
    private static final String DEFAULT_BROWSER = "Firefox";
    private static final String DEFAULT_URL = "http://www.google.com";
    private static final String PASSWORD = System.getenv("PASSWORD");

    private void takeScreenShot(WebDriver driver) throws IOException {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./" + currentTime + "_screenshot.png"));
    }

    @Test
    public void testFirefox1() throws Exception {
//        System.setProperty("webdriver.gecko.driver", "./lib/geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    // Using default profile of firefox
    public void testFirefox2() throws Exception {
        ProfilesIni profileIni = new ProfilesIni();
        FirefoxProfile profile = profileIni.getProfile("default-release");
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions().setProfile(profile);
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        // takeScreenShot(driver);
        // driver.quit();
    }

    @Test
    // Using profile by path
    public void testFirefox3() throws Exception {
        WebDriverManager.firefoxdriver().setup();
        String pathProfile = System.getenv("HOMEPATH")
                + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\obztpb5l.default-release";
        FirefoxProfile profile = new FirefoxProfile(new File(pathProfile));
        FirefoxOptions options = new FirefoxOptions().setProfile(profile);
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        // takeScreenShot(driver);
        // driver.quit();
    }


    @Test
    // Firefox's headless mode
    // https://developer.mozilla.org/en-US/Firefox/Headless_mode
    public void testFirefox4() throws Exception {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions()
                //.setBinary("c:/Program Files/Nightly/firefox.exe")
                .addArguments("-headless")
                .addArguments("-console");
        WebDriver driver = new FirefoxDriver(options);
        System.out.println("***Start");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        System.out.println("***title1 = " + driver.getTitle());
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        System.out.println("***title2 = " + driver.getTitle());
        takeScreenShot(driver);
        driver.quit();
    }


    @Test
    // Using Untrusted Certificates
    public void testFirefox5() throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true); // true by default
        // options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, false);
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://speak-ukrainian.org.ua/");
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    public void testChrome1() throws Exception {
        // System.setProperty("webdriver.chrome.driver",
        // this.getClass().getResource("/chromedriver-windows-32bit.exe").getPath());
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    // chrome command line arguments
    // https://peter.sh/experiments/chromium-command-line-switches/
    public void testChrome2() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-proxy-server");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-fullscreen"); // F11 in browser
        // options.addArguments("--disable-extensions");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        // takeScreenShot(driver);
        driver.quit();
    }

    @Test
    // ignore-certificate-errors
    public void testChrome3Certificate() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-proxy-server");
        options.addArguments("--ignore-certificate-errors"); // NOT working by default!!!
        // options.addArguments("--disable-web-security");
        // options.addArguments("--disable-machine-cert-request");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://speak-ukrainian.org.ua/");
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    // Profile by Path
    public void testChrome4UserProfile() throws Exception {
        WebDriverManager.chromedriver().setup();
        String userProfile = System.getenv("HOMEPATH")
                + "\\AppData\\Local\\Google\\Chrome\\User Data";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-web-security");
        //options.addArguments("--ignore-certificate-errors");
        options.addArguments("--user-data-dir=" + userProfile);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        takeScreenShot(driver);
        //driver.quit();
    }

    // @Test
    // Specified location
    public void testChrome5() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-proxy-server");
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    // Chrome Without GUI
    public void testChrome6() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Chrome Without GUI
        WebDriver driver = new ChromeDriver(options);
        System.out.println("\tStart");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        System.out.println("\tdriver.get http://www.google.com DONE");
        System.out.println("\tCurrent title is: " + driver.getTitle());
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(1000);
        System.out.println("\tPage title is: " + driver.getTitle());
        Thread.sleep(2000);
        takeScreenShot(driver);
        driver.quit();
    }

    @Test
    public void testHtmlUnit() throws Exception {
        WebDriver driver = new HtmlUnitDriver(false);
        //((HtmlUnitDriver) driver).setJavascriptEnabled(true); // TODO Enable CSS
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.get("https://www.google.com.ua");
        System.out.println("\tdriver.get http://www.google.com DONE");
        System.out.println("\tCurrent title is: " + driver.getTitle());
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        System.out.println("\telement.sendKeys Cheese! DONE");
        System.out.println("\tPage title is: " + driver.getTitle());
        element.submit();
        Thread.sleep(1000);
        System.out.println("\tPage title is: " + driver.getTitle());
        driver.findElement(By.partialLinkText("Cheese - Wikipedia")).click();
        System.out.println("\tELEMENT IS " + driver.findElement(By.id("firstHeading")).getText());
        System.out.println("\tPage title is: " + driver.getTitle());
        Assertions.assertEquals(driver.findElement(By.id("firstHeading")).getText(), "Cheese");
        Thread.sleep(2000);
        // takeScreenShot(driver); // Do not use!!!
        driver.quit();
    }

    // Download from http://phantomjs.org/download.html
    // Use Selenium 4.7.1
    @Test
    public void testPhantomjs() throws Exception {
        //System.setProperty("phantomjs.binary.path", "./lib/phantomjs.exe");
        // WebDriverManager.phantomjs().setup();  // io.github.bonigarcia. Use version 4.4.3
        WebDriver driver = new PhantomJSDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        System.out.println("\tdriver.get http://www.google.com DONE");
        System.out.println("\tCurrent title is: " + driver.getTitle());
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        System.out.println("\telement.sendKeys Cheese! DONE");
        System.out.println("\tPage title is: " + driver.getTitle());
        element.submit();
        Thread.sleep(1000);
        System.out.println("\tPage title is: " + driver.getTitle());
        System.out.println("\tTaking First Screenshot ...");
        Thread.sleep(2000);
        takeScreenShot(driver);
        System.out.println("\tDone Screenshot");
        driver.quit();
    }

    @BeforeAll
    public static void createService() throws Exception {
        service = new ChromeDriverService.Builder()
                // .usingDriverExecutable(new File("./lib/chromedriver.exe"))
                .usingDriverExecutable(new File(System.getenv("HOMEPATH")
                        + "\\.cache\\selenium\\chromedriver\\win64\\125.0.6422.141\\chromedriver.exe"))
                // .usingAnyFreePort()
                .usingPort(8888).build();
        service.start();
        System.out.println("\t+++Service Start");
    }

    @Test
    public void testRemout() throws Exception {
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
        System.out.println("\t+++RemoteWebDriver Start, service.getUrl()=" + service.getUrl());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Cheese!");
        element.submit();
        Thread.sleep(4000);
        takeScreenShot(driver);
        driver.quit();
        System.out.println("\t+++driver.quit()");
    }

    @AfterAll
    public static void StopService() {
        if (service != null) {
            service.stop();
            System.out.println("\t+++RemoteWebDriver Stop");
        }
    }

}
