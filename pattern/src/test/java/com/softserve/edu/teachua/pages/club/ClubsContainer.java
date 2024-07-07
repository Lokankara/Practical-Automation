package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.exceptions.ComponentNotFoundException;
import com.softserve.edu.teachua.pages.top.BaseComponent;
import com.softserve.edu.teachua.pages.top.TopPart;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ClubsContainer extends BaseComponent {
    public static final String CLUBS_NOT_FOUND = "There is no club that matches the search criteria.";
    private static final String CLUBS_COMPONENT_CSS = "div.ant-card.ant-card-bordered";

    private static final String PAGINATION_NUMBERS_XPATH = "//li[contains(@class,'ant-pagination-item')]/a[text()='%d']";
    private List<ClubComponent> clubComponents;

    public ClubsContainer() {
        initElements();
    }

    private void initElements() {
        clubComponents = new ArrayList<>();
        try { // TODO remove sleep
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        search.cssSelectors(CLUBS_COMPONENT_CSS).forEach(current -> clubComponents.add(new ClubComponent(current)));
        if (clubComponents.isEmpty()) {
            throw new ComponentNotFoundException(CLUBS_NOT_FOUND);
        }
    }

    public List<ClubComponent> getClubComponents() {
        return clubComponents;
    }

    public WebElement getPreviousItem() {
        return search.cssSelector("li[title='Previous Page']");
    }

    public String getPreviousItemClassAttribute() {
        return getPreviousItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
    }

    public boolean isEnablePreviousPageLink() {
        return !getPreviousItemClassAttribute().contains("pagination-disabled");
    }

    public WebElement getNextItem() {
        return search.cssSelector("li[title='Next Page']");
    }

    public String getNextItemClassAttribute() {
        return getNextItem().getAttribute(TopPart.TAG_ATTRIBUTE_CLASS);
    }

    public boolean isEnableNextPageLink() {
        return !getNextItemClassAttribute().contains("pagination-disabled");
    }

    public WebElement getPreviousPageLink() {
        return search.cssSelector("li[title='Previous Page'] > button");
    }

    public String getPreviousPageLinkText() {
        return getPreviousPageLink().getText();
    }

    public void clickPreviousPageLink() {
        getPreviousPageLink().click();
    }

    public WebElement getNextPageLink() {
        return search.cssSelector("li[title='Next Page'] > button");
    }

    public String getNextPageLinkText() {
        return getNextPageLink().getText();
    }

    public void clickNextPageLink() {
        getNextPageLink().click();
    }

    public int getClubComponentsCount() {
        return getClubComponents().size();
    }

    public List<String> getClubComponentTitles() {
        return getClubComponents().stream().map(ClubComponent::getTitleLinkText).toList();
    }

    public ClubComponent getFirstClubComponent() {
        if (getClubComponentsCount() == 0) {
            throw new ComponentNotFoundException(CLUBS_NOT_FOUND);
        }
        return getClubComponents().get(0);
    }

    public ClubComponent getClubComponentByTitle(String clubTitle) {
        return getClubComponents().stream()
                .filter(current -> current.getTitleLinkText()
                        .equalsIgnoreCase(clubTitle))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(
                        "ClubTitle: ", clubTitle, " not Found."));
    }

    public ClubComponent getClubComponentByPartialTitle(String partialTitle) {
        return getClubComponents()
                .stream()
                .filter(current -> current.getTitleLinkText().toLowerCase()
                        .contains(partialTitle.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(
                        "Club partialTitle: ", partialTitle, " not Found."));
    }

    public boolean isExistClubComponentByPartialTitle(String partialTitle) {
        return getClubComponentTitles()
                .stream()
                .anyMatch(current -> current.toLowerCase()
                        .contains(partialTitle.toLowerCase()));
    }

    public void clickPageLinkByNumber(int numberPage) {
        List<WebElement> paginationNumbers = search.xpaths(String.format(PAGINATION_NUMBERS_XPATH, numberPage));
        if (!paginationNumbers.isEmpty()) {
            paginationNumbers.get(0).click();
        }
        throw new ComponentNotFoundException("Pagination Number: ", String.valueOf(numberPage), " not Found.");
    }
}
