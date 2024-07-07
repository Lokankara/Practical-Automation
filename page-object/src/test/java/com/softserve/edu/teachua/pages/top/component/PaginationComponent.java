package com.softserve.edu.teachua.pages.top.component;

import com.softserve.edu.teachua.exception.PaginationException;
import com.softserve.edu.teachua.pages.club.ClubPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaginationComponent {
    public PaginationComponent(WebDriver driver) {
        this.driver = driver;
        initElements();
    }

    private final WebDriver driver;
    private WebElement nextPageLink;
    private WebElement previousPageLink;
    public static final String ATTRIBUTE_CLASS_NAME = "class";
    public static final String ATTRIBUTE_ARIA_NAME = "aria-disabled";
    private static final By PREV_PAGE = By.xpath("//li[@title='Previous Page']");
    private static final By NEXT_PAGE = By.xpath("//li[@title='Next Page']");

    private void initElements() {
        previousPageLink = driver.findElement(PREV_PAGE);
        nextPageLink = driver.findElement(NEXT_PAGE);
    }

    private WebElement getPreviousPageLink() {
        return previousPageLink;
    }

    public void clickPreviousPageLink() {
        getPreviousPageLink().click();
    }

    public boolean isEnablePreviousPageLink() {
        return !(isDisable(getPreviousPageLink()));
    }

    private WebElement getNextPageLink() {
        return nextPageLink;
    }

    public void clickNextPageLink() {
        getNextPageLink().click();
    }

    public boolean isEnableNextPageLink() {
        return !isDisable(nextPageLink);
    }

    public ClubPage chooseClubPaginationNumber(int numberPage) {
        clickPageLinkByNumber(numberPage);
        return new ClubPage(driver);
    }

    public ClubPage previousClubPagination() {
        if (!isEnablePreviousPageLink()) {
            throw new PaginationException("The previous page is not available");
        }
        clickPreviousPageLink();
        return new ClubPage(driver);
    }

    private boolean isDisable(WebElement page) {
        boolean parsed = Boolean.parseBoolean(getAttribute(page, ATTRIBUTE_ARIA_NAME));
        boolean isDisabled = getAttribute(page, ATTRIBUTE_CLASS_NAME).contains("ant-pagination-disabled");
        return isDisabled && parsed;
    }

    private String getAttribute(WebElement page, String name) {
        return page.getAttribute(name);
    }

    public void clickPageLinkByNumber(int numberPage) {
        try {
            driver.findElement(By.xpath(String.format("//li[@title='%d']/a", numberPage))).click();
        } catch (NoSuchElementException e) {
            throw new PaginationException(String.format("Page number '%d' element not found", numberPage), e.getMessage());
        }
    }
}
