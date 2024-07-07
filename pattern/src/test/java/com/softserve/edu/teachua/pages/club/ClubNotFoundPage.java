package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import org.openqa.selenium.WebElement;

public class ClubNotFoundPage extends TopSearchPart {
    public static String NOT_FOUND_MASSAGE = "За критеріями пошуку гуртків не знайдено";
    private WebElement notFoundLabel;

    public ClubNotFoundPage() {
        super();
        initElements();
    }

    private void initElements() {
        notFoundLabel = search.cssSelector("div.clubs-not-found");
    }

    public WebElement getNotFoundLabel() {
        return notFoundLabel;
    }

    public String getNotFoundLabelText() {
        return getNotFoundLabel().getText();
    }
}
