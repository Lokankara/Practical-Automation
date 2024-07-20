package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class ClubNotFoundPage extends TopSearchPart {
    public static String NOT_FOUND_MASSAGE = "За критеріями пошуку гуртків не знайдено";

    private WebElement notFoundLabel;

    public ClubNotFoundPage() {
        initElements();
    }

    private void initElements() {
        // init elements
        notFoundLabel = search.cssSelector("div.clubs-not-found");
    }

    // Page Object

    // notFoundLabel
    public WebElement getNotFoundLabel() {
        return notFoundLabel;
    }

    public String getNotFoundLabelText() {
        return getNotFoundLabel().getText();
    }

    // Functional

    // Business Logic

}
