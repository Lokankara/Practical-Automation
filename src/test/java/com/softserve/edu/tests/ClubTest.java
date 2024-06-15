package com.softserve.edu.tests;

import com.softserve.edu.manager.Configuration;
import com.softserve.edu.provider.CommentArgumentsProvider;
import com.softserve.edu.reporter.LoggerUtils;
import com.softserve.edu.runner.BaseTestSuite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Execution(ExecutionMode.CONCURRENT)
public class ClubTest extends BaseTestSuite {
    private static final String RATE_XPATH = "(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]";
    private static final String USER_PROFILE_XPATH = "div.user-profile span.anticon.anticon-caret-down";

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed",
                String.valueOf(Thread.currentThread().getName()));
    }

    @BeforeEach
    void setupThis() {
        isSuccess = false;
        driver.get(Configuration.getInstance().getBaseURL());
        LoggerUtils.logInfo("@BeforeEach executed",
                String.valueOf(Thread.currentThread().getName()));
    }

    @AfterEach
    void tearThis() {
        LoggerUtils.logInfo("User Logout", "@AfterEach executed",
                String.valueOf(Thread.currentThread().getName()));
        if (!isSuccess) {
            takeScreenShot(driver);
        }
    }

    @ParameterizedTest(name = "Test comment for user {0} in club {2}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testCommentUser(String username, String password, String clubName, String userComment) {
        LoggerUtils.logInfo(username, password);
        performUserAction("login");
        signIn(username, password);
        openClub(clubName);
        leaveComment(userComment);
        verifyUserComment(userComment);
        performUserAction("logout");
        isSuccess = true;
    }

    private void verifyUserComment(String userComment) {
        WebElement comment = getVisibleElement(By.xpath("//div[@class='ant-comment-content-detail']/p"));
        assertEquals(userComment, comment.getText(), "Verify user comment is displayed correctly");
    }

    private void leaveComment(String userComment) {
        fillAndAssertFieldById("comment-edit_commentText", userComment);
        getLastStar().click();
        driver.findElement(By.cssSelector("button.do-comment-button")).click();
    }

    private void openClub(String clubName) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ant-modal-wrap.ant-modal-centered")));
        WebElement clubsMenuItem = getClickableElement(By.xpath("//a[contains(text(),'Гуртки')]"));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);
        getClickableElement(By.xpath("//div[contains(text(),'" + clubName + "')]/ancestor::div[contains(@class,'ant-card-body')]//a[contains(@href, '/dev/club/')]")).click();
        driver.findElement(By.cssSelector("button.comment-button")).click();
    }

    protected void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    protected void scrollToElement(WebElement element) {
        assertNotNull(element, "Element should be present");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    private void performUserAction(String action) {
        getClickableElement(By.cssSelector(USER_PROFILE_XPATH)).click();
        String actionSelector = "li[data-menu-id*='" + action + "'] span.ant-dropdown-menu-title-content";
        getClickableElement(By.cssSelector(actionSelector)).click();
    }

    protected WebElement getVisibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement getClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement getLastStar() {
        return driver.findElement(By.xpath(RATE_XPATH));
    }

    private void signIn(String mail, String password) {
        fillAndAssertFieldById("basic_email", mail);
        fillAndAssertFieldById("basic_password", password);
        driver.findElement(By.cssSelector("div.login-footer button")).click();
    }

    protected void fillAndAssertFieldById(String xPath, String value) {
        WebElement field = driver.findElement(By.id(xPath));
        assertNotNull(field, String.format("Field with XPath '%s' should be present", xPath));
        field.clear();
        field.sendKeys(value);
    }
}
