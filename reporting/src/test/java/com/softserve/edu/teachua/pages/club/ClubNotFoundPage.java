package com.softserve.edu.teachua.pages.club;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class ClubNotFoundPage extends TopSearchPart {
    public static String NOT_FOUND_MASSAGE = "За критеріями пошуку гуртків не знайдено";

    private WebElement notFoundLabel;

    public ClubNotFoundPage() {
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements on ClubNotFoundPage");
        notFoundLabel = search.cssSelector("div.clubs-not-found");
    }

    public WebElement getNotFoundLabel() {
        ReportUtils.logInfo("Getting Not Found label element");
        return notFoundLabel;
    }

    public String getNotFoundLabelText() {
        String text = getNotFoundLabel().getText();
        ReportUtils.logInfo("Got Not Found label text: " + text);
        return text;
    }
}
