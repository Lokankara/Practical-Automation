package com.softserve.edu.teachua.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class SimpleTest extends BaseTest {
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;

    @BeforeEach
    public void beforeEach() {
        getDriver().get(BASE_URL);
    }

    @AfterEach
    public void afterEach() {
        deleteCookies();
        clearStorage();
    }

    @Test
    void checkAddComment() {
        openLoginModal();
        login();
        getDriver().findElement(By.cssSelector("div.login-footer button")).click();
        getDriver().findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']")).click();
        getDriver().findElement(By.cssSelector("input.ant-select-selection-search-input")).sendKeys("IT освіта");
        Assertions.assertTrue(getDriver().findElement(By.xpath("//div[contains(@class,'ant-card-body')]//div[contains(text(),'IT освіта: курси')]")).getText().contains("ГРАНД"));
        getDriver().findElement(By.xpath("//div[@class='ant-card-body']//div[contains(text(),'IT освіта: курси')]/../../a[contains(@href, '/club/')]")).click();
        getDriver().findElement(By.cssSelector("button.comment-button")).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        new WebDriverWait(getDriver(), Duration.ofSeconds(5L)).until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]"))).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        getDriver().findElement(By.id("comment-edit_commentText")).click();
        getDriver().findElement(By.id("comment-edit_commentText")).clear();
        getDriver().findElement(By.id("comment-edit_commentText")).sendKeys("Проба Коментар");
        getDriver().findElement(By.cssSelector("button.do-comment-button")).click();
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        WebElement comment = new WebDriverWait(getDriver(), Duration.ofSeconds(5L))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ant-comment-content-detail']/p)[position()=1]")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Assertions.assertEquals("Проба Коментар", comment.getText());
        logOut();
    }

    private void login() {
        WebElement email = getDriver().findElement(By.id("basic_email"));
        WebElement password = getDriver().findElement(By.id("basic_password"));
        email.click();
        email.clear();
        email.sendKeys("yagifij495@eqvox.com");
        password.click();
        password.clear();
        password.sendKeys("Qwerty_1");
    }

    @Test
    void checkLogin() {
        openLoginModal();
        login();
        getDriver().findElement(By.cssSelector("div.login-footer button")).click();
        WebElement cityName = getDriver().findElement(By.cssSelector("h2.city-name"));
        Assertions.assertTrue(cityName.getText().contains("Навчай українською"));
        logOut();
    }

    private void openLoginModal() {
        getDriver().findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        getDriver().findElement(By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content")).click();
    }

    private void logOut() {
        getDriver().findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(5L)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("li[data-menu-id*='logout'] span.ant-dropdown-menu-title-content"))).click();
    }

    @Test
    void testClubExist() {
        getDriver().findElement(By.cssSelector("input.ant-select-selection-search-input")).sendKeys("Школа");
        WebElement titleClub = getDriver().findElement(By.xpath("//div[contains(@class,'ant-card')]//div[contains(text(),'Dream Team')]"));
        Assertions.assertEquals("Школа танців Dream Team", titleClub.getText());
        titleClub.click();
    }
}
