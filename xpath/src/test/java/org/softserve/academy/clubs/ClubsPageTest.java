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
import org.softserve.academy.runner.ClubsBaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Clubs Page Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClubsPageTest extends ClubsBaseTest {

    private static final String SEND_COMMENT_XPATH = "//button[contains(@class, 'do-comment-button')]";
    private static final String AUTH_MESSAGE_ERROR_SELECTOR = "//div[@class='ant-message-custom-content ant-message-error']";
    private static final String ACTIVE_PAGE = "//*[contains(@class, 'ant-pagination-item-active')]/*[self::a]";
    private static final String LOGO_XPATH = "//div[@class='logo']";
    private static final String EXPECTED_GRAND_COURSE = "IT освіта: курси \"ГРАНД\"";

    @Test
    @DisplayName("Test finding and clicking on 'Clubs' menu item")
    void testClickClubsMenuItem() {
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

        assertEquals("2", getVisibleElement(By.xpath(ACTIVE_PAGE)).getText(), "Current page should be 2");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test clicking on club card and navigating to details page")
    void testClickClubCard() {
        loginUser(EMAIL, PASSWORD);
        openSecondPage();
        WebElement button = getGrandCardButton();
        String hrefDetails = button.getAttribute("href");
        clickElementWithJS(button);

        assertNotNull(hrefDetails, "Club title href should be present");
        assertEquals(driver.getCurrentUrl(), hrefDetails, "Current URL and button href should be the same, indicating navigation to the correct page");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button when user Unauthorized")
    void testClickLeaveCommentButtonWhenUnauthorizedUser() {
        final String expectedMessage = "Увійдіть або зареєструйтеся!";
        openGrandCardOnSecondPage();
        WebElement leaveCommentButton = getClickableElement(By.xpath(LEAVE_COMMENT_BUTTON_XPATH));
        scrollToElement(leaveCommentButton);
        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);
        assertEnable(leaveCommentButton, LEAVE_COMMENT_MESSAGE);
        clickElementWithJS(leaveCommentButton);

        assertTextEquals(expectedMessage, getVisibleElement(By.xpath(AUTH_MESSAGE_ERROR_SELECTOR)), "Error Message text");
        isTestSuccessful = true;
    }

    @DisplayName("Test finding and clicking 'Leave a Comment' button")
    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test finding and clicking 'Leave a Comment' button when a user {3} {2} logged in")
    void testClickLeaveCommentButton(String email, String password, String lastName, String firstName, String phone) {
        loginUser(email, password);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement nameInput = getVisibleElement(By.xpath("//*[contains(@class, 'ant-input') and contains(@class, 'comment-input-box')]"));
        WebElement clubTitleNote = getVisibleElement(By.xpath("//*[contains(@class, 'club-title-note')]"));
        WebElement commentEdit = getVisibleElement(By.xpath("//*[@id='comment-edit_commentText']"));
        WebElement doCommentButton = getVisibleElement(By.xpath("//*[contains(@class, 'do-comment-button')]"));

        assertTrue(nameInput.getAttribute("value").contains(lastName), "Expected lastName in the input box");
        assertTrue(nameInput.getAttribute("value").contains(firstName), "Expected firstName in the input box");

        assertNotNull(nameInput.getAttribute("readonly"), "Expected input to be read-only");
        assertEquals(EXPECTED_GRAND_COURSE, clubTitleNote.getText(), "Expected the club title note to be 'GRAND_COURSE'");
        assertTrue(commentEdit.isEnabled(), "Expected the comment edit box to be enabled");
        assertFalse(doCommentButton.isEnabled(), "Expected the do comment button to be disabled");
        isTestSuccessful = true;
    }

    @CsvFileSource(resources = "/sign.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Test happy path writing a comment and submitting when a user {3} {2} logged in")
    @DisplayName("Test writing a comment and submitting")
    void testWriteCommentAndSubmit(String email, String password, String lastName, String firstName, String phone, String comment)  {
        final String expectedComments = "Коментарі";
        final String expectedComment = "Додайте коментар";
        loginUser(email, password);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        WebElement commentsHeader = getVisibleElement(By.xpath("//span[@class='comment-label']"));
        WebElement commentText = getVisibleElement(By.xpath(COMMENT_TEXT_XPATH));
        WebElement rate = getLastStar();

        assertEnable(commentText, "Comment text field area");
        assertEquals(expectedComment, commentText.getAttribute("placeholder"), "Placeholder text should match 'Додайте коментар'");
        assertEnable(rate, "Rate field");
        assertTextEquals(expectedComments, commentsHeader, "Comment header");
        clickElementWithJS(rate);
        commentText.sendKeys(comment);
        WebElement sendButton = getClickableElement(By.xpath(SEND_COMMENT_XPATH));
        assertEnable(sendButton, "Send button");
        clickElementWithJS(sendButton);

        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment and submitting Max Limit")
    void testCommentLengthExceedsMaxLimit() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        WebElement commentText = getVisibleElement(By.xpath(COMMENT_TEXT_XPATH));
        clickElementWithJS(getLastStar());
        commentText.sendKeys("1234567890".repeat(256));

        WebElement sendButton = getVisibleElement(By.xpath(SEND_COMMENT_XPATH));

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
        clickElementWithJS(getLastStar());

        sendComment("a".repeat(number));

        WebElement sendButton = getVisibleElement(By.xpath(SEND_COMMENT_XPATH));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when comment is less than 10 characters");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment with valid context Without Rating Submission")
    void testSubmissionWithoutRating() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        WebElement commentText = getVisibleElement(By.xpath(COMMENT_TEXT_XPATH));
        commentText.sendKeys("Send button should be disabled");
        WebElement sendButton = getVisibleElement(By.xpath(SEND_COMMENT_XPATH));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when no rating");
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test writing a comment with valid context with Double Clicking on the rating")
    void testSubmissionDoubleClickingToRating() {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();

        new Actions(driver).doubleClick(getLastStar()).perform();

        WebElement commentText = getVisibleElement(By.xpath(COMMENT_TEXT_XPATH));
        commentText.sendKeys("Send button should be disabled");
        WebElement sendButton = getVisibleElement(By.xpath(SEND_COMMENT_XPATH));

        assertFalse(sendButton.isEnabled(), "Send button should be disabled when no rating");
        isTestSuccessful = true;
    }

    @ParameterizedTest(name = "Test writing a comment with Special Symbols {0}")
    @ArgumentsSource(SpecialSymbolsProvider.class)
    @DisplayName("Test writing a comment with Special Symbols context")
    void testSubmissionWithSpecialSymbols(String symbol, boolean isAllow) {
        loginUser(EMAIL, PASSWORD);
        openGrandCardOnSecondPage();
        clickLeaveCommentButton();
        WebElement commentText = getVisibleElement(By.xpath(COMMENT_TEXT_XPATH));
        clickElementWithJS(getLastStar());

        commentText.sendKeys(symbol);
        WebElement sendButton = getVisibleElement(By.xpath(SEND_COMMENT_XPATH));

        assertEquals(sendButton.isEnabled(), isAllow, "Send button should be disabled when no rating with symbol " + symbol);
        isTestSuccessful = true;
    }

    @Test
    @DisplayName("Test finding the logo in the left-side menu")
    void testFindLogoInLeftSideMenu() {
        WebElement logo = getVisibleElement(By.xpath(LOGO_XPATH));

        assertTrue(logo.isDisplayed(), "Logo should be visible in the left-side menu");
        isTestSuccessful = true;
    }
}
