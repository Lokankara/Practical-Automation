package org.softserve.academy.clubs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.softserve.academy.runner.ClubsBaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Clubs Page Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClubsPageTest extends ClubsBaseTest {

    private static final String SECOND_PAGE_LINK_CSS = "li[class$='ant-pagination-item-2'] a[rel='nofollow']";
    private static final String CLUBS_MENU_ITEM_CSS = "span.ant-menu-title-content a[href='/dev/clubs']";
    private static final String LOGO_SELECTOR_CSS = ".left-side-menu .logo";

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
        openTabClubs();
        openSecondPage();
        isTestSuccessful = true;
        //TODO
    }

    @Test
    @DisplayName("Test clicking on club card and navigating to details page")
    void testClickClubCard() {
        openTabClubs();
        isTestSuccessful = true;
        //TODO
    }

    @Test
    @DisplayName("Test finding and clicking 'Leave a Comment' button")
    void testClickLeaveCommentButton() {
        //TODO
    }

    @Test
    @DisplayName("Test writing a comment and submitting")
    void testWriteCommentAndSubmit() {
        //TODO
    }

    @Test
    @DisplayName("Test finding the logo in the left-side menu")
    void testFindLogoInLeftSideMenu() {
        WebElement logo = getVisibleElement(By.cssSelector(LOGO_SELECTOR_CSS));
        assertTrue(logo.isDisplayed(), "Logo should be visible in the left-side menu");
        isTestSuccessful = true;
    }

    private void openSecondPage() {
        openTabClubs();
        WebElement secondPage = getClickableElement(By.cssSelector(SECOND_PAGE_LINK_CSS));
        scrollToElement(secondPage);
        clickElementWithJS(secondPage);
    }

    private void openTabClubs() {
        WebElement clubsMenuItem = getClickableElement(By.cssSelector(CLUBS_MENU_ITEM_CSS));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);
    }
}
