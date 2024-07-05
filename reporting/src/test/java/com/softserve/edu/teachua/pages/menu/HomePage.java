package com.softserve.edu.teachua.pages.menu;

import com.softserve.edu.teachua.pages.top.TopSearchPart;
import com.softserve.edu.teachua.pages.user.LoginModal;
import com.softserve.edu.teachua.tools.ReportUtils;
import org.openqa.selenium.WebElement;

public class HomePage extends TopSearchPart {

    public static final String BEGIN_TEACH_LABEL_MESSAGE = "Ініціатива";

    private WebElement addClubButton;
    private WebElement teachLabel;

    public HomePage() {
        ReportUtils.logInfo("Initializing HomePage");
        initElements();
    }

    private void initElements() {
        ReportUtils.logInfo("Initializing elements in HomePage");
        addClubButton = search.cssSelector("button.add-club-button");
        ReportUtils.logInfo("Initialized addClubButton element");
        teachLabel = search.cssSelector("div.city-name-box h2.city-name");
        ReportUtils.logInfo("Initialized teachLabel element");
    }

    public WebElement getAddClubButton() {
        ReportUtils.logInfo("Retrieving addClubButton element");
        return addClubButton;
    }

    public String getAddClubButtonText() {
        String text = getAddClubButton().getText();
        ReportUtils.logInfo("Retrieved addClubButton text: " + text);
        return text;
    }

    public void clickAddClubButton() {
        ReportUtils.logAction("Clicking addClubButton");
        getAddClubButton().click();
    }

    public WebElement getTeachLabel() {
        ReportUtils.logInfo("Retrieving teachLabel element");
        return teachLabel;
    }

    public String getTeachLabelText() {
        String text = getTeachLabel().getText();
        ReportUtils.logInfo("Retrieved teachLabel text: " + text);
        return text;
    }

    public LoginModal openLoginModalAddClub() {
        ReportUtils.logAction("Opening LoginModal by clicking addClubButton");
        clickAddClubButton();
        return new LoginModal();
    }
}
