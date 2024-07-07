package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.data.ClubContents;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ClubComponent {
    private final WebDriver driver;
    private final WebElement clubCard;
    private WebElement clubTitleLink;
    private WebElement addressLabel;
    private WebElement detailsButton;
    private List<WebElement> categoriesLabel;
    private WebElement clubDescriptionLabel;
    private static final By ADDRESS = By.cssSelector("div.address");
    private static final By DESCRIPTION = By.cssSelector("p.description");
    private static final By TITLE = By.cssSelector("div.title > div.name");
    private static final By DETAILS_BUTTON = By.cssSelector("a.details-button > a");
    private static final By CLUB_TAGS = By.cssSelector("div.tags.club-tags span.name");

    public ClubComponent(WebDriver driver, WebElement clubCard) {
        this.driver = driver;
        this.clubCard = clubCard;
        initElements();
    }

    private void initElements() {
        clubTitleLink = getClubCard().findElement(TITLE);
        categoriesLabel = getClubCard().findElements(CLUB_TAGS);
        clubDescriptionLabel = getClubCard().findElement(DESCRIPTION);
        addressLabel = getClubCard().findElement(ADDRESS);
        detailsButton = getClubCard().findElement(DETAILS_BUTTON);
    }

    private WebElement getClubCard() {
        return clubCard;
    }

    public String getClubTitleLinkText() {
        return getClubTitleLink().getText();
    }

    private List<WebElement> getCategoriesLabel() {
        return categoriesLabel;
    }

    public List<String> getCategoriesLabelText() {
        return getCategoriesLabel().stream().map(WebElement::getText).toList();
    }

    private WebElement getClubDescriptionLabel() {
        return clubDescriptionLabel;
    }

    public String getDescriptionLabelText() {
        return getClubDescriptionLabel().getText();
    }

    private WebElement getAddressLabel() {
        return addressLabel;
    }

    public String getAddressLabelText() {
        return getAddressLabel().getText();
    }

    private WebElement getDetailsButton() {
        return detailsButton;
    }

    public void clickDetailsButton() {
        getDetailsButton().click();
    }

    protected WebElement getClubTitleLink() {
        return clubTitleLink;
    }

    private void clickClubTitleLink() {
        getClubTitleLink().click();
    }

    public ClubInfoModal openClubInfoModal() {
        clickClubTitleLink();
        return new ClubInfoModal(driver);
    }

    public ClubDetailsPage openClubDetailsPage() {
        clickDetailsButton();
        return new ClubDetailsPage(driver);
    }

    public ClubComponent assertClubContent(ClubContents content) {
        assertClubAddress(content.getCity());
        assertClubTitle(content.getClub());
        assertClubDescription(content.getDescriptions());
        return this;
    }

    private void assertClubDescription(String description) {
        Assertions.assertTrue(getDescriptionLabelText().contains(description),
                String.format("Description does not contain expected city: %s but was %s", description, getDescriptionLabelText()));
    }

    private void assertClubTitle(String clubName) {
        Assertions.assertTrue(getClubTitleLinkText().contains(clubName),
                String.format("Club Title does not contain expected city: %s but was %s", clubName, getClubTitleLinkText()));
    }

    public void assertClubAddress(Cities content) {
        Assertions.assertTrue(getAddressLabelText().contains(content.getCity()),
                String.format("Address does not contain expected city: %s but was %s", content.getCity(), getAddressLabelText()));
    }
}
