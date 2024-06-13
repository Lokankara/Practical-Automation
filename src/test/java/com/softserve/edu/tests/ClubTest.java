package com.softserve.edu.tests;

import com.softserve.edu.manager.Configuration;
import com.softserve.edu.manager.DriverManager;
import com.softserve.edu.provider.CommentArgumentsProvider;
import com.softserve.edu.reporter.LoggerUtils;
import com.softserve.edu.runner.BaseTestSuite;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Execution(ExecutionMode.SAME_THREAD)
public class ClubTest extends BaseTestSuite {

    private static final String RATE_XPATH = "(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]";
    private static final String USER_PROFILE_XPATH = "div.user-profile span.anticon.anticon-caret-down";
    private static final String BASE_URL = Configuration.getInstance().getBaseURL();

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed ThreadId",
                String.valueOf(Thread.currentThread().getName()));
    }

    @AfterAll
    public static void tear() {
        DriverManager.quitAll();
        LoggerUtils.logPass("@AfterAll executed",
                String.valueOf(Thread.currentThread().getName()));
    }

    @BeforeEach
    public void setupThis() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Configuration.getInstance().getDuration());
        driver.get(Configuration.getInstance().getBaseURL());
        LoggerUtils.logInfo("@BeforeEach executed",
                String.valueOf(Thread.currentThread().getName()));
    }

    @AfterEach
    public void tearThis() {
        LoggerUtils.logInfo("@AfterEach executed");
    }

    @ParameterizedTest(name = "Test comment for user {0} in club {2}")
    @ArgumentsSource(CommentArgumentsProvider.class)
    void testCommentUser(String username, String password, String clubName, String userComment) {
        driver.get(BASE_URL);
        performUserAction("login");
        signIn(username, password);
        openClub(clubName);
        leaveComment(userComment);
        verifyUserComment(userComment);
        performUserAction("logout");
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
        getClickableElement(By.xpath("//span[@class='ant-menu-title-content']/a[text()='Гуртки']")).click();
        getClickableElement(By.xpath("//div[@class='ant-card-body']//div[contains(text(),'" + clubName + "')]/../../a[contains(@href, '/club/')]")).click();
        driver.findElement(By.cssSelector("button.comment-button")).click();
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
