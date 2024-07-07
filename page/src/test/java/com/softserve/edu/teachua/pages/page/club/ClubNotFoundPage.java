package com.softserve.edu.teachua.pages.page.club;

import com.softserve.edu.teachua.pages.part.TopSearchPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClubNotFoundPage extends TopSearchPart {
    public static final By CLUBS_NOT_FOUND = By.cssSelector("div.clubs-not-found");
    public static final String NOT_FOUND_MASSAGE = "За критеріями пошуку гуртків не знайдено";

    private WebElement notFoundLabel;

    public ClubNotFoundPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    private void initElements() {
        notFoundLabel = getElement(CLUBS_NOT_FOUND);
    }

    public WebElement getNotFoundLabel() {
        return notFoundLabel;
    }

    public String getNotFoundLabelText() {
        return getNotFoundLabel().getText();
    }
}
