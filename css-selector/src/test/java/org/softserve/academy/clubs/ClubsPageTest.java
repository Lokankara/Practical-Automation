package org.softserve.academy.clubs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.provider.SpecialSymbolsProvider;
import org.softserve.academy.runner.BaseTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Clubs Page Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClubsPageTest extends BaseTest {
    private static final String CLUBS_MENU_ITEM_CSS = "span.ant-menu-title-content a[href='/dev/clubs']";
    private static final String SECOND_PAGE_LINK_CSS = "li[class$='ant-pagination-item-2'] a[rel='nofollow']";
    private static final String LEAVE_COMMENT_BUTTON_SELECTOR = "button.comment-button";
    private static final String CONTENT_SELECTOR = ".ant-layout-content .content";
    private static final String LOGO_SELECTOR_CSS = ".left-side-menu .logo";
    private static final String MESSAGE_ERROR_SELECTOR = "div.ant-message-error";
    private static final String CARD_BORDERED = ".ant-card.ant-card-bordered";
    private static final String COMMENT_TEXT_SELECTOR = "#comment-edit_commentText";
    private static final String FIELD_INPUT_SELECTOR = ".edit-field-input";
    private static final String RATE_STARS_CSS_SELECTOR = ".ant-rate-star";
    private static final String CONTENT_CLUBS_LIST = "content-clubs-list";
    private static final String CLUB_CARD_NAME_SELECTOR = ".club-name";
    private static final String SEND_COMMENT_CSS_SELECTOR = "button.do-comment-button";
    private static final String LOGIN_HEADER_SELECTOR = ".login-header";
    private static final String USER_ICON_CSS_SELECTOR = "svg[data-icon='user']";
    private static final String LOGIN_MENU_ITEM_XPATH = "//li[@role='menuitem']//div[text()='Увійти']";
    private static final String GRAND_COURSE = "IT освіта: курси \"ГРАНД\"";
    private static final String LEAVE_COMMENT_MESSAGE = "Leave Comment Button";
    private static final String EXPECTED_COMMENT_HEADER = "Залишити коментар";
    private static final String EMAIL = "nenix55377@hutov.com";
    private static final String PASSWORD = "Elv3nWay!";

    @Test
    @DisplayName("Test finding and clicking on 'Clubs' menu item")
    void testClickClubsMenuItem() {
        loginUser(EMAIL, PASSWORD);
        openTabClubs();

        String expectedUrl = BASE_URL + "/clubs";
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl(), "URL should be the clubs page URL");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test navigating to second page")
    void testNavigateToSecondPage() {
        loginUser(EMAIL, PASSWORD);
        openSecondPage();

        WebElement currentPageIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-pagination-item-active > a")));
        assertEquals("2", currentPageIndicator.getText(), "Current page should be 2");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test clicking on club card and navigating to details page")
    void testClickClubCard() {
        loginUser(EMAIL, PASSWORD);
        openSecondPage();
        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.cssSelector("a > a"));
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
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button when user Unauthorized")
    void testClickLeaveCommentButtonWhenUnauthorizedUser() {
        final String expectedMessage = "Увійдіть або зареєструйтеся!";
        openGrandCardOnSecondPage();

        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);

        assertEnable(leaveCommentButton, LEAVE_COMMENT_MESSAGE);
        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);

        clickElementWithJS(leaveCommentButton);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MESSAGE_ERROR_SELECTOR)));

        assertTextEquals(expectedMessage, errorMessage, "Error Message text");
        isTestSuccessful = true;
    }

    @DisplayName("Test finding and clicking 'Leave a Comment' button")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test finding and clicking 'Leave a Comment' button when a user {3} {2} logged in")
    void testClickLeaveCommentButton(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-input.comment-input-box")));
        WebElement clubTitleNote = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".club-title-note")));
        WebElement commentEdit = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#comment-edit_commentText")));
        WebElement doCommentButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".do-comment-button")));
        List<WebElement> inputElements = driver.findElements(By.cssSelector(".ant-input-affix-wrapper-readonly.comment-input-box .ant-input"));

        assertTrue(nameInput.getAttribute("value").contains(lastName), "Expected lastName in the input box");
        assertTrue(nameInput.getAttribute("value").contains(firstName), "Expected firstName in the input box");

        assertTrue(inputElements.get(0).getAttribute("value").contains(phone), "Expected phone in the input box");
        assertEquals(email, inputElements.get(1).getAttribute("value"), "Expected email in the input box");
        assertNotNull(inputElements.get(0).getAttribute("readonly"), "Expected input to be read-only");
        assertNotNull(inputElements.get(1).getAttribute("readonly"), "Expected input to be read-only");
        assertNotNull(nameInput.getAttribute("readonly"), "Expected input to be read-only");
        assertEquals(GRAND_COURSE, clubTitleNote.getText(), "Expected the club title note to be 'GRAND_COURSE'");
        assertTrue(commentEdit.isEnabled(), "Expected the comment edit box to be enabled");
        assertFalse(doCommentButton.isEnabled(), "Expected the do comment button to be disabled");
        isTestSuccessful = true;
    }

    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test happy path writing a comment and submitting when a user {3} {2} logged in")
    @DisplayName("Test writing a comment and submitting")
    void testWriteCommentAndSubmit(String email, String password, String lastName, String firstName, String phone, String comment) {
        final String expectedComments = "Коментарі";
        final String expectedComment = "Додайте коментар";
        loginUser(email, password);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement commentsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".comment-label")));
        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        WebElement rate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FIELD_INPUT_SELECTOR)));
        List<WebElement> rateElements = rate.findElements(By.cssSelector(RATE_STARS_CSS_SELECTOR));

        assertEnable(commentText, "Comment text field area");
        assertEquals(expectedComment, commentText.getAttribute("placeholder"), "Placeholder text should match 'Додайте коментар'");
        assertEnable(rate, "Rate field");
        assertTextEquals(expectedComments, commentsHeader, "Comment header");

        rateElements.get(new Random().nextInt(rateElements.size())).click();
        commentText.sendKeys(comment);
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));
        assertTrue(sendButton.isEnabled());

        clickElementWithJS(sendButton);
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment and submitting Max Limit")
    void testCommentLengthExceedsMaxLimit() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        clickRandomStar();
        commentText.sendKeys("1234567890".repeat(256));

        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));
        assertTrue(sendButton.isEnabled(), "Send button should be disabled when comment exceeds 12800 characters");
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "Test writing a comment with context Length Too Short: {0}")
    @ValueSource(ints = {0, 1, 5, 8, 9})
    @DisplayName("Test writing a comment with context Length Too Short")
    void testCommentLengthTooShort(int number) {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        clickRandomStar();
        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        commentText.sendKeys("a".repeat(number));

        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when comment is less than 10 characters");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment with valid context Without Rating Submission")
    void testSubmissionWithoutRating() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        commentText.sendKeys("Send button should be disabled");
        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when no rating");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment with valid context Without Rating Submission")
    void testSubmissionDoubleClickingToRating() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement rate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FIELD_INPUT_SELECTOR)));
        List<WebElement> rateElements = rate.findElements(By.cssSelector(RATE_STARS_CSS_SELECTOR));
        WebElement webElement = rateElements.get(new Random().nextInt(rateElements.size()));

        new Actions(driver).doubleClick(webElement).perform();

        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        commentText.sendKeys("Send button should be disabled");
        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when no rating");
        isTestSuccessful = true;
    }

    @ParameterizedTest
    @ArgumentsSource(SpecialSymbolsProvider.class)
    @DisplayName("Test writing a comment with Special Symbols context {0}")
    void testSubmissionWithSpecialSymbols(String symbol, boolean isAllow) {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        WebElement commentText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMMENT_TEXT_SELECTOR)));
        clickRandomStar();

        commentText.sendKeys(symbol);
        WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SEND_COMMENT_CSS_SELECTOR)));

        assertEquals(sendButton.isEnabled(), isAllow, "Send button should be disabled when no rating with symbol " + symbol);
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test finding the logo in the left-side menu")
    void testFindLogoInLeftSideMenu() {
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGO_SELECTOR_CSS)));
        assertTrue(logo.isDisplayed(), "Logo should be visible in the left-side menu");
        isTestSuccessful = true;
    }

    private void clickLeaveCommentButton() {
        WebElement leaveCommentButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(LEAVE_COMMENT_BUTTON_SELECTOR)));
        scrollToElement(leaveCommentButton);
        clickElementWithJS(leaveCommentButton);
        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);
    }

    private void clickRandomStar() {
        WebElement rate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(FIELD_INPUT_SELECTOR)));
        List<WebElement> rateElements = rate.findElements(By.cssSelector(RATE_STARS_CSS_SELECTOR));
        rateElements.get(new Random().nextInt(rateElements.size())).click();
    }

    private WebElement getCardBy(String name) {
        WebElement clubContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(CONTENT_CLUBS_LIST)));
        List<WebElement> clubCards = clubContainer.findElements(By.cssSelector(CARD_BORDERED));
        assertFalse(clubCards.isEmpty(), "The clubCards list should not be empty");
        WebElement courseCard = clubCards.stream().filter(clubCard -> clubCard.getText().contains(name)).findFirst().orElse(null);
        scrollToElement(courseCard);
        return courseCard;
    }

    private void openGrandCardOnSecondPage() {
        openSecondPage();
        WebElement clubCard = getCardBy(GRAND_COURSE);
        WebElement button = clubCard.findElement(By.cssSelector("a > a"));
        assertEnable(button, "Card details");
        clickElementWithJS(button);
    }

    private void openSecondPage() {
        openTabClubs();
        WebElement secondPage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SECOND_PAGE_LINK_CSS)));
        scrollToElement(secondPage);
        clickElementWithJS(secondPage);
    }

    private void openTabClubs() {
        WebElement clubsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CLUBS_MENU_ITEM_CSS)));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);
    }

    private void loginUser(String email, String password) {
        WebElement userIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(USER_ICON_CSS_SELECTOR)));
        scrollToElement(userIcon);
        clickElementWithJS(userIcon);

        WebElement loginMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGIN_MENU_ITEM_XPATH)));
        assertNotNull(loginMenuItem, "Login MenuItem should be present");
        assertTrue(loginMenuItem.isDisplayed(), "Login MenuItem should be visible after clicking the 'Login' menu item");
        scrollToElement(loginMenuItem);
        clickElementWithJS(loginMenuItem);
        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LOGIN_HEADER_SELECTOR)));
        assertNotNull(loginHeader, "Login modal should be visible after clicking the 'Login' menu item");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#basic_email"))).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#basic_password"))).sendKeys(password);
        clickElementWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".login-button"))));
    }
}
