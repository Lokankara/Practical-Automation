package org.softserve.academy.runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class ClubsBaseTest extends BaseTest {
    protected static final String SECOND_PAGE_LINK_XPATH = "//li[contains(@class, 'ant-pagination-item-2')]//a[@rel='nofollow']\n";
    protected static final String LEAVE_COMMENT_BUTTON_XPATH = "//span[contains(text(),'Залишити коментар')]";
    protected static final String COMMENT_TEXT_XPATH = "//*[@id='comment-edit_commentText']";
    protected static final String LEAVE_COMMENT_MESSAGE = "Leave Comment Button";
    protected static final String EXPECTED_COMMENT_HEADER = "Залишити коментар";

    protected void clickLeaveCommentButton() {
        WebElement leaveCommentButton = getClickableElement(By.xpath(LEAVE_COMMENT_BUTTON_XPATH));
        scrollToElement(leaveCommentButton);
        clickElementWithJS(leaveCommentButton);
        assertTextEquals(EXPECTED_COMMENT_HEADER, leaveCommentButton, LEAVE_COMMENT_MESSAGE);
    }

    protected WebElement getLastStar() {
        return getClickableElement(By.xpath("(//*[@id='comment-edit_rate']//li[contains(@class, 'ant-rate-star')]/div)[last()]"));
    }

    protected WebElement getGrandCardButton() {
        WebElement courseCard = getVisibleElement(By.xpath("//div[contains(text(), 'IT освіта: курси \"ГРАНД\"')]/ancestor::div[contains(@class, 'ant-card-body')]//a[text()='Детальніше']"));
        scrollToElement(courseCard);
        return courseCard;
    }

    protected void openGrandCardOnSecondPage() {
        openSecondPage();
        WebElement clubCard = getGrandCardButton();
        assertEnable(clubCard, "Card details");
        clickElementWithJS(clubCard);
    }

    protected void openSecondPage() {
        openTabClubs();
        WebElement secondPage = getClickableElement(By.xpath(SECOND_PAGE_LINK_XPATH));
        scrollToElement(secondPage);
        clickElementWithJS(secondPage);
    }

    protected void openTabClubs() {
        WebElement clubsMenuItem = getClickableElement(By.xpath("//a[contains(text(),'Гуртки')]"));
        scrollToElement(clubsMenuItem);
        clickElementWithJS(clubsMenuItem);
    }

    protected void sendComment(String comment) {
        getVisibleElement(By.xpath(COMMENT_TEXT_XPATH)).sendKeys(comment);
    }
}
