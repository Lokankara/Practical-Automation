package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ClubComponent extends BaseComponent {

    private final WebElement clubCard;

    public ClubComponent(WebElement clubCard) {
        this.clubCard = clubCard;
    }

    public WebElement getTitleLink() {
        return clubCard.findElement(By.cssSelector("div.title"));
    }

    public List<WebElement> getCategoriesLabel() {
        return clubCard.findElements(By.cssSelector("div.tags.club-tags span.name"));
    }

    public WebElement getDescriptionLabel() {
        return clubCard.findElement(By.cssSelector("p.description"));
    }

    public WebElement getAddressLabel() {
        return clubCard.findElement(By.cssSelector("div.address"));
    }

    public WebElement getDetailsButton() {
        return clubCard.findElement(By.cssSelector("a.details-button > a"));
    }


    public String getTitleLinkText() {
        return getTitleLink().getText();
    }

    public void clickTitleLink() {
        getTitleLink().click();
    }

    public List<String> getCategoriesLabelText() {
        return getCategoriesLabel().stream().map(WebElement::getText).toList();
    }
    public String getDescriptionLabelText() {
        return getDescriptionLabel().getText();
    }

    public String getAddressLabelText() {
        return getAddressLabel().getText();
    }

    public String getDetailsButtonText() {
        return getDetailsButton().getText();
    }

    public void clickDetailsButton() {
        getDetailsButton().click();
    }

    public ClubInfoBaseModal openClubInfoModal() {
        clickTitleLink();
        return new ClubInfoBaseModal();
    }

    public ClubDetailsPage openClubDetailsPage() {
        clickDetailsButton();
        return new ClubDetailsPage();
    }
}
