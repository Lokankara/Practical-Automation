package com.softserve.edu.tests;

import com.softserve.edu.runner.BaseTestSuite;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ClubTest extends BaseTestSuite {
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;

    @BeforeAll
    public static void setup() {
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setupThis() {
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis() {
        System.out.println("\t@AfterEach executed");
        logout();
        if (driver != null) {
            driver.quit();
        }
    }

    private void logout() {
    }

    @Test
    public void testOne() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
        Thread.sleep(2000); // For Presentation
        //
        driver.get(BASE_URL);
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys("yagifij495@eqvox.com");
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("Qwerty_1");
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.login-footer button")).click();
        Thread.sleep(4000); // For Presentation
        //
        driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.xpath("//div[@class='ant-card-body']//div[contains(text(),'IT освіта: курси')]/../../a[contains(@href, '/club/')]")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("button.comment-button")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.ant-form-item div[aria-posinset='5']")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("comment-edit_commentText")).click();
        driver.findElement(By.id("comment-edit_commentText")).clear();
        driver.findElement(By.id("comment-edit_commentText")).sendKeys("Проба Коментар");
        Thread.sleep(8000); // For Presentation
        //
        driver.findElement(By.cssSelector("button.do-comment-button")).click();
        Thread.sleep(2000); // For Presentation
        //
        // Check
        WebElement comment = driver.findElement(By.xpath(("//div[@class='ant-comment-content']//span[contains(text(), 'Проба')]/../../../../../div[@class='ant-comment-content-detail']/p")));
        Assertions.assertEquals("Проба Коментар", comment.getText());
        //
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("li[data-menu-id*='logout'] span.ant-dropdown-menu-title-content")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.quit();
    }

    @Test
    public void testTwo() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        driver.manage().window().maximize();
        Thread.sleep(2000); // For Presentation
        //
        driver.get(BASE_URL);
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys("user@gmail.com");
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("user");
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.login-footer button")).click();
        Thread.sleep(4000); // For Presentation
        //
        driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.xpath("//div[@class='ant-card-body']//div[contains(text(),'Онлайн-школа точних наук')]/../../a[contains(@href, '/club/')]")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("button.comment-button")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("div.ant-form-item div[aria-posinset='5']")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.id("comment-edit_commentText")).click();
        driver.findElement(By.id("comment-edit_commentText")).clear();
        driver.findElement(By.id("comment-edit_commentText")).sendKeys("User Коментар");
        Thread.sleep(8000); // For Presentation
        //
        driver.findElement(By.cssSelector("button.do-comment-button")).click();
        Thread.sleep(2000); // For Presentation
        //
        // Check
        WebElement comment = driver.findElement(By.xpath(("//div[@class='ant-comment-content']//span[contains(text(), 'user')]/../../../../../div[@class='ant-comment-content-detail']/p")));
        Assertions.assertEquals("User Коментар", comment.getText());
        //
        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.findElement(By.cssSelector("li[data-menu-id*='logout'] span.ant-dropdown-menu-title-content")).click();
        Thread.sleep(2000); // For Presentation
        //
        driver.quit();
    }
}
