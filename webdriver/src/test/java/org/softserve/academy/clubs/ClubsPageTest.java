package org.softserve.academy.clubs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
    private static final String CONTENT_SELECTOR = ".ant-layout-content .content";
    private static final String SECOND_PAGE_LINK_XPATH = "//li[@title='2']/a";
    private static final String MESSAGE_ERROR_SELECTOR = "div.ant-message-error";
    private static final String MODAL_CONTENT_SELECTOR = ".ant-modal-content";
    private static final String CARD_BORDERED = ".ant-card.ant-card-bordered";
    private static final String COMMENT_TEXT_ID = "comment-edit_commentText";
    private static final String FIELD_INPUT_SELECTOR = ".edit-field-input";
    private static final String RATE_STARS_CSS_SELECTOR = ".ant-rate-star";
    private static final String CONTENT_CLUBS_LIST = "content-clubs-list";
    private static final String CLUB_CARD_NAME_SELECTOR = ".club-name";
    private static final String GRAND_COURSE = "IT освіта: курси \"ГРАНД\"";
    private static final String CLUB_CARD_NAME_CLASS = "name";
    private static final String LEAVE_COMMENT_MESSAGE = "Leave Comment Button";
    private static final String EXPECTED_COMMENT_HEADER = "Залишити коментар";
    private static final String EMAIL = "nenix55377@hutov.com";
    private static final String PASSWORD = "Elv3nWay!";

    @BeforeAll
    public void setUpAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        if ("true".equals(System.getenv("CI"))) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        executor = (JavascriptExecutor) driver;
    }

    @BeforeEach
    public void setUpEach() {
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDownEach() {
        executor.executeScript("window.localStorage.clear()");
        driver.manage().deleteAllCookies();
    }

    @Test
    @Order(1)
    @DisplayName("Test finding and clicking on 'Clubs' menu item")
    void testClickClubsMenuItem() {
        loginUser(EMAIL, PASSWORD);
        WebElement clubsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CLUBS_MENU_ITEM_XPATH)));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);

        String expectedUrl = BASE_URL + "clubs";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL should be the clubs page URL");
        logOutUser();
    }

    @Test
    @Order(2)
    @DisplayName("Test navigating to second page")
    void testNavigateToSecondPage() {
        loginUser(EMAIL, PASSWORD);

        WebElement clubs = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CLUBS_MENU_ITEM_XPATH)));
        scrollToElement(clubs);
        clickElementWithJS(clubs);
        WebElement secondPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SECOND_PAGE_LINK_XPATH)));
        scrollToElement(secondPage);
        clickElementWithJS(secondPage);

        WebElement currentPageIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-pagination-item-active > a")));
        assertEquals("2", currentPageIndicator.getText(), "Current page should be 2");
    }

    @Test
    @Order(3)
    @DisplayName("Test clicking on club card and navigating to details page")
    void testClickClubCard() {
        testNavigateToSecondPage();

        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.tagName("a")).findElement(By.tagName("a"));
        WebElement header = clubCard.findElement(By.cssSelector(".title .name"));
        WebElement desc = clubCard.findElement(By.cssSelector(".description"));

        assertTextPresent(header, "Club card title header");
        assertTextPresent(desc, "Club card description");
        assertTextPresent(button, "Club card button");

        String headerText = header.getText();
        String descText = desc.getText();
        String hrefDetails = button.getAttribute("href");
        clickElementWithJS(button);

        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CONTENT_SELECTOR)));
        WebElement clubName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_CARD_NAME_SELECTOR)));

        assertNotNull(hrefDetails, "Club title href should be present");
        assertEquals(driver.getCurrentUrl(), hrefDetails, "Current URL and button href should be the same, indicating navigation to the correct page");
        assertTextEquals(descText, content, "Content text and description text");
        assertTextEquals(headerText, clubName, "Header text and club name");
    }

    @Test
    @Order(4)
    @DisplayName("Test clicking on club card header and check details")
    void testClickClubCardModalWindowDetails() {
        testNavigateToSecondPage();

        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.tagName("a"));
        WebElement header = clubCard.findElement(By.className(CLUB_CARD_NAME_CLASS));

        clickElementWithJS(header);

        WebElement clubNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_CARD_NAME_SELECTOR)));
        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CLUB_DESCRIPTION_SELECTOR)));
        WebElement buttonDetails = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button a")));
        String hrefDetails = button.getAttribute("href");
        String linkAttribute = buttonDetails.getAttribute("href");
        String[] expectedParts = linkAttribute.split("/");
        String[] actualParts = hrefDetails.split("/");

        assertNotNull(hrefDetails, "Club title url should be present");
        assertEnable(buttonDetails, "Button Details");
        assertEnable(button, "Club header");
        assertTextPresent(header, "Club title header");
        assertTextEquals(header.getText(), clubNameElement, "Club name in modal");
        assertTextPresent(description, "Club description");
        assertEquals(expectedParts[expectedParts.length - 1], actualParts[actualParts.length - 1], "Last parts of URLs should be equal");
        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(MODAL_CONTENT_SELECTOR))), "Modal should be closed");
    }

    @Test
    @Order(5)
    @DisplayName("Test finding and clicking 'Leave a Comment' button when user Unauthorized")
    void testClickLeaveCommentButton() {
        final String expectedMessage = "Увійдіть або зареєструйтеся!";

        openGrandCardOnSecondPage();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);

        assertEnable(leaveCommentButton, LEAVE_COMMENT_MESSAGE);
        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);

        clickElementWithJS(leaveCommentButton);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MESSAGE_ERROR_SELECTOR)));

        assertTextEquals(expectedMessage, errorMessage, "Error Message text");
    }

    @Order(5)
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test finding and clicking 'Leave a Comment' button when a user {3} {2} logged in")
    void testClickLeaveCommentButtonWhenTheUserIsLoggedIn(String email, String password, String lastName, String firstName) {
        loginUser(email, password);
        openGrandCardOnSecondPage();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);

        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);

        clickElementWithJS(leaveCommentButton);
        WebElement commentModalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_EDIT_TITLE_SELECTOR)));

        assertTextEquals(EXPECTED_COMMENT_HEADER, commentModalTitle, "Comment Modal Title");
    }

    @Order(6)
    @DisplayName("Test writing a comment and submitting")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test writing a comment {5} and submitting by {3} {2} ")
    void testWriteCommentAndSubmit(String email, String password, String lastName, String firstName, String phone, String comment) {
        final String expectedComments = "Коментарі";
        final String expectedComment = "Додайте коментар";
        loginUser(email, password);

        openGrandCardOnSecondPage();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);
        clickElementWithJS(leaveCommentButton);

        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(COMMENT_TEXT_ID)));
        WebElement rate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FIELD_INPUT_SELECTOR)));
        List<WebElement> rateElements = rate.findElements(By.cssSelector(RATE_STARS_CSS_SELECTOR));

        assertEnable(commentText, "Comment text field area");
        assertEquals(expectedComment, commentText.getAttribute("placeholder"), "Placeholder text should match 'Додайте коментар'");
        assertEnable(rate, "Rate field");

        rateElements.get(getRandomIndex(rateElements)).click();
        commentText.sendKeys(comment);
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));
        clickElementWithJS(sendButton);
        WebElement commentsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("comment-label")));
        List<WebElement> comments = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(COMMENTS_SELECTOR)));

        assertEnable(commentsHeader, "Comments header");
        assertTextEquals(expectedComments, commentsHeader, "Comment header");
        assertFalse(comments.isEmpty(), "Comments list should not be empty");
        assertTextEquals(comment, comments.get(0), "Last comment paragraph");
    }

    private void openGrandCardOnSecondPage() {
        WebElement clubsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CLUBS_MENU_ITEM_XPATH)));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);

        WebElement secondPageLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SECOND_PAGE_LINK_XPATH)));
        scrollToElement(secondPageLink);
        clickElementWithJS(secondPageLink);

        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.tagName("a")).findElement(By.tagName("a"));
        clickElementWithJS(button);
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

    private static void assertVisible(WebElement element, String message) {
        assertNotNull(element, String.format("%s should be visible", message));
        assertTrue(element.isDisplayed(), String.format("%s should be displayed", message));
    }

    private void assertEnable(WebElement element, String message) {
        assertVisible(element, message);
        assertTrue(element.isEnabled(), String.format("%s should be enabled", message));
    }

    private void assertTextPresent(WebElement element, String message) {
        assertVisible(element, message);
        assertNotNull(element.getText(), String.format("%s should be not null", message));
    }

    private void assertTextEquals(String expected, WebElement element, String message) {
        assertTextPresent(element, message);
        assertEquals(expected, element.getText(), String.format("%s text should match %s", message, expected));
    }

    private WebElement getCardBy(String name) {
        WebElement clubContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(CONTENT_CLUBS_LIST)));
        List<WebElement> clubCards = clubContainer.findElements(By.cssSelector(CARD_BORDERED));
        assertFalse(clubCards.isEmpty(), "The clubCards list should not be empty");
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
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @AfterAll
    public void tearDownAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}
