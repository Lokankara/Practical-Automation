package org.softserve.academy.clubs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Clubs Page Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClubsPageTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor executor;

    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final String CLUBS_MENU_ITEM_XPATH = "span.ant-menu-title-content > a[href='/dev/clubs']";
    private static final String LEAVE_COMMENT_BUTTON_SELECTOR = "button.comment-button";
    private static final String COMMENTS_SELECTOR = ".ant-comment-content-detail > p";
    private static final String CLUB_DESCRIPTION_SELECTOR = ".about-club .description";
    private static final String SEND_COMMENT_CSS_SELECTOR = "button.do-comment-button";
    private static final String COMMENT_EDIT_TITLE_SELECTOR = ".comment-edit-title";
    private static final String SECOND_PAGE_LINK_XPATH = "//li[@title='2']/a";
    private static final String MESSAGE_ERROR_SELECTOR = "div.ant-message-error";
    private static final String CARD_BORDERED = ".ant-card.ant-card-bordered";
    private static final String COMMENT_TEXT_ID = "comment-edit_commentText";
    private static final String FIELD_INPUT_SELECTOR = ".edit-field-input";
    private static final String RATE_STARS_CSS_SELECTOR = ".ant-rate-star";
    private static final String CONTENT_CLUBS_LIST = "content-clubs-list";
    private static final String CLUB_CARD_NAME_SELECTOR = ".club-name";
    private static final String GRAND_COURSE = "IT освіта: курси \"ГРАНД\"";
    private static final String CLUB_CARD_NAME_CLASS = "name";
    private static final String COMMENT = "Look Good For Me";
    final String email = "nenix55377@hutov.com";
    final String password = "Elv3nWay!";

    @BeforeAll
    public void setUpAll() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-sh-usage");
        chromeOptions.addArguments("--remote-debugging-port=9222");
        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        executor = (JavascriptExecutor) driver;
//        driver.get(BASE_URL);
    }

    @BeforeEach
    public void setUpEach() {
        driver.get(BASE_URL);
        loginUser(email, password);
    }

    @AfterEach
    public void tearDownEach() {
        executor.executeScript("window.localStorage.clear()");
        driver.manage().deleteAllCookies();
    }

    @Test
    @DisplayName("Test finding and clicking on 'Clubs' menu item")
    void testClickClubsMenuItem() {
        WebElement clubsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CLUBS_MENU_ITEM_XPATH)));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);

        String expectedUrl = BASE_URL + "clubs";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL should be the clubs page URL");
    }

    @Test
    @DisplayName("Test navigating to second page")
    void testNavigateToSecondPage() {
        testClickClubsMenuItem();

        WebElement secondPageLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SECOND_PAGE_LINK_XPATH)));
        scrollToElement(secondPageLink);
        clickElementWithJS(secondPageLink);

        WebElement currentPageIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-pagination-item-active > a")));
        assertEquals("2", currentPageIndicator.getText(), "Current page should be 2");
    }

    @Test
    @DisplayName("Test clicking on club card and navigating to details page")
    void testClickClubCard() {
        testNavigateToSecondPage();

        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.tagName("a")).findElement(By.tagName("a"));
        WebElement header = clubCard.findElement(By.cssSelector(".title .name"));
        WebElement desc = clubCard.findElement(By.cssSelector(".description"));

        assertTextNotNull(header, "Club card title header");
        assertTextNotNull(desc, "Club card description");
        assertTextNotNull(button, "Club card button");

        String headerText = header.getText();
        String descText = desc.getText();
        String hrefDetails = button.getAttribute("href");
        clickElementWithJS(button);

        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-layout-content .content")));
        WebElement clubName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_CARD_NAME_SELECTOR)));

        assertNotNull(hrefDetails, "Club title should be present");
        assertEquals(driver.getCurrentUrl(), hrefDetails, "Current URL and button href should be the same, indicating navigation to the correct page");
        assertEquals(content.getText(), descText, "Content text and description text");
        assertEquals(clubName.getText(), headerText, "Dont the same page");
    }

    @Test
    @DisplayName("Test clicking on club card header and check details")
    void testClickClubCardModalWindowDetails() {
        testNavigateToSecondPage();

        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.tagName("a"));
        WebElement header = clubCard.findElement(By.className(CLUB_CARD_NAME_CLASS));

        clickElementWithJS(header);

        WebElement clubNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_CARD_NAME_SELECTOR)));
        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_DESCRIPTION_SELECTOR)));
        WebElement buttonLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button a")));

        assertPresent(buttonLink, "button Link name");
        assertClickable(button,"Club header");

        String hrefDetails = button.getAttribute("href");

        assertNotNull(hrefDetails, "Club title url should be present");
        assertTextNotNull(header, "Club title header");
        assertTextEquals(header.getText(), clubNameElement, "Club name in modal");
        assertTextNotNull(description, "Club description");

        String linkAttribute = buttonLink.getAttribute("href");
        String[] expectedParts = linkAttribute.split("/");
        String[] actualParts = hrefDetails.split("/");

        assertEquals(expectedParts[expectedParts.length - 1], actualParts[actualParts.length - 1], "Last parts of URLs should be equal");
        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ant-modal-content"))), "Modal should be closed");
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button when user Unauthorized")
    void testClickLeaveCommentButton() {
        final String expectedComment = "Залишити коментар";
        final String expectedMessage = "Увійдіть або зареєструйтеся!";
        logOutUser();
        testClickClubCard();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);

        assertClickable(leaveCommentButton, "Leave Comment Button's ");
        assertTextEquals(expectedComment, leaveCommentButton, "Leave Comment Button's");

        clickElementWithJS(leaveCommentButton);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MESSAGE_ERROR_SELECTOR)));

        assertTextEquals(expectedMessage, errorMessage, "Error Message text");
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button when a user logged in")
    void testClickLeaveCommentButtonWhenTheUserIsLoggedIn() {
        final String expectedComment = "Залишити коментар";
        testClickClubCard();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);

        assertTextEquals(expectedComment, leaveCommentButton, "Leave Comment Button");

        clickElementWithJS(leaveCommentButton);
        WebElement commentModalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_EDIT_TITLE_SELECTOR)));

        assertTextEquals(expectedComment, commentModalTitle, "Comment Modal Title");
    }

    @Test
    @DisplayName("Test writing a comment and submitting")
    void testWriteCommentAndSubmit() {
        final String expectedComments = "Коментарі";
        final String expectedComment = "Додайте коментар";
        testClickLeaveCommentButtonWhenTheUserIsLoggedIn();

        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(COMMENT_TEXT_ID)));
        WebElement rate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FIELD_INPUT_SELECTOR)));
        List<WebElement> rateElements = rate.findElements(By.cssSelector(RATE_STARS_CSS_SELECTOR));

        assertClickable(commentText,"Comment text field area");
        assertEquals(expectedComment, commentText.getAttribute("placeholder"), "Placeholder text should match 'Додайте коментар'");
        assertClickable(rate, "Rate field");

        rateElements.get(getRandomIndex(rateElements)).click();
        commentText.sendKeys(COMMENT);
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));
        clickElementWithJS(sendButton);
        WebElement commentsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("comment-label")));
        List<WebElement> comments = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(COMMENTS_SELECTOR)));

        assertClickable(commentsHeader, "Comments header");
        assertTextEquals(expectedComments, commentsHeader, "Comment header");
        assertFalse(comments.isEmpty(), "Comments list should not be empty");
        assertTextEquals(COMMENT, comments.get(0), "Last comment paragraph");
    }

    private void loginUser(String email, String password) {
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("svg[data-icon='user']"))));
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='menuitem']//div[text()='Увійти']"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("basic_email"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("basic_password"))).sendKeys(password);
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".login-button"))));
    }

    void logOutUser() {
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.ant-dropdown-trigger.user-profile"))));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.ant-dropdown-menu-item.ant-dropdown-menu-item-danger"))).click();
    }

    private static int getRandomIndex(List<WebElement> rateElements) {
        return new Random().nextInt(rateElements.size());
    }

    private static void assertPresent(WebElement element, String message) {
        assertNotNull(element, String.format("%s should be visible", message));
        assertTrue(element.isDisplayed(), String.format("%s should be displayed", message));
    }
    private void assertClickable(WebElement element, String message) {
        assertPresent(element, message);
        assertTrue(element.isEnabled(), String.format("%s should be enabled", message));
    }

    private void assertTextNotNull(WebElement element, String message) {
        assertPresent(element, message);
        assertNotNull(element.getText(), String.format("%s should be not null", message));
    }

    private void assertTextEquals(String expected, WebElement element, String message) {
        assertTextNotNull(element, message);
        assertEquals(expected, element.getText(), String.format("%s text should match %s", message, expected));
    }

    private WebElement getCardBy(String name) {
        WebElement clubContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(CONTENT_CLUBS_LIST)));
        List<WebElement> clubCards = clubContainer.findElements(By.cssSelector(CARD_BORDERED));
        assertFalse(clubCards.isEmpty());
        WebElement courseCard = clubCards.stream().filter(clubCard -> clubCard.getText().contains(name)).findFirst().orElse(null);
        scrollToElement(courseCard);
        return courseCard;
    }

    private void clickElementWithJS(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            executor.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));", element);
        } else {
            throw new IllegalArgumentException("Element is not clickable: " + element);
        }
    }

    private void scrollToElement(WebElement element) {
        assertNotNull(element, "Element is not present");
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterAll
    public void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}
