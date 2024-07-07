package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.exceptions.ChallengeSelectionException;
import com.softserve.edu.teachua.exceptions.DropdownNotFoundException;
import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.pages.menu.AboutUsPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.menu.NewsPage;
import com.softserve.edu.teachua.pages.menu.UkrainianServicePage;
import com.softserve.edu.teachua.pages.user.GuestDropdown;
import com.softserve.edu.teachua.pages.user.LoggedDropdown;
import com.softserve.edu.teachua.pages.user.LoginModal;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.List;

public abstract class TopPart {
    protected static final Logger logger = Logger.getLogger(TopPart.class.getName());
    public static final String POPUP_MESSAGE_SUCCESSFULLY = "Ви успішно залогувалися!";
    public static final String TAG_ATTRIBUTE_VALUE = "value";
    public static final String TAG_ATTRIBUTE_CLASS = "class";
    public static final String TAG_ATTRIBUTE_HREF = "href";
    public static final String OPTION_NOT_FOUND_MESSAGE = "Option %s not found in %s";
    protected final String OPTION_NULL_MESSAGE = "Dropdown is null";
    public static final String POPUP_MESSAGE_CSS = "div.ant-message-notice-wrapper span:last-child";
    protected final String LIST_CHALLENGE_CSS = "a[href*='/challenges']";
    protected final String LIST_CITY_CSS = "ul.ant-dropdown-menu span";

    protected Search search;
    private WebElement homeLink;
    private WebElement clubLink;
    private WebElement challengeLink;
    private WebElement newsLink;
    private WebElement aboutUsLink;
    private WebElement ukrainianServiceLink;
    private WebElement caretDropdownLink;
    private WebElement qubStudioLabel;
    private WebElement userProfilePic;
    private DropdownComponent dropdownComponent;
    private GuestDropdown dropdownGuest;
    private LoggedDropdown dropdownLogged;

    public TopPart() {
        search = SearchStrategy.getSearch();
        initElements();
        //checkElements();
    }

    private void initElements() {
        homeLink = search.cssSelector("div.left-side-menu > a");
        clubLink = search.cssSelector("span.ant-menu-title-content > a[href*='/clubs']");
        challengeLink = search.cssSelector("span.challenge-text");
        newsLink = search.cssSelector("span.ant-menu-title-content > a[href*='/news']");
        aboutUsLink = search.cssSelector("span.ant-menu-title-content > a[href*='/about']");
        ukrainianServiceLink = search.cssSelector("span.ant-menu-title-content > a[href*='/service']");
        caretDropdownLink = search.cssSelector("div.user-profile span.anticon.anticon-caret-down");
        qubStudioLabel = search.cssSelector("div.qubstudio");
        userProfilePic = search.cssSelector("div.user-profile span.ant-avatar");
    }

    public WebElement getHomeLink() {
        return homeLink;
    }

    public String getHomeLinkText() {
        return getHomeLink().getText();
    }

    public void clickHomeLink() {
        getHomeLink().click();
    }

    public WebElement getClubLink() {
        return clubLink;
    }

    public String getClubLinkText() {
        return getClubLink().getText();
    }

    public void clickClubLink() {
        getClubLink().click();
    }

    public WebElement getChallengeLink() {
        return challengeLink;
    }

    public String getChallengeLinkText() {
        return getChallengeLink().getText();
    }

    public void clickChallengeLink() {
        getChallengeLink().click();
    }

    public WebElement getNewsLink() {
        return newsLink;
    }

    public String getNewsLinkText() {
        return getNewsLink().getText();
    }

    public void clickNewsLink() {
        getNewsLink().click();
    }

    public WebElement getAboutUsLink() {
        return aboutUsLink;
    }

    public String getAboutUsLinkText() {
        return getAboutUsLink().getText();
    }

    public void clickAboutUsLink() {
        getAboutUsLink().click();
    }

    public WebElement getUkrainianServiceLink() {
        return ukrainianServiceLink;
    }

    public String getUkrainianServiceLinkText() {
        return getUkrainianServiceLink().getText();
    }

    public void clickUkrainianServiceLink() {
        getUkrainianServiceLink().click();
    }

    public WebElement getQubStudioLabel() {
        return qubStudioLabel;
    }

    public String getQubStudioLabelText() {
        return getQubStudioLabel().getText();
    }

    public void clickQubStudioLabel() {
        getQubStudioLabel().click();
    }

    public WebElement getCityDropdownLink() {
        return search.cssSelector("div.city span.anticon-caret-down");
    }

    public void clickCityDropdownLink() {
        getCityDropdownLink().click();
    }

    public WebElement getCaretDropdownLink() {
        return caretDropdownLink;
    }

    public void clickCaretDropdownLink() {
        getCaretDropdownLink().click();
    }

    public WebElement getUserProfilePic() {
        return userProfilePic;
    }

    protected DropdownComponent getDropdownComponent() {
        if (dropdownComponent == null) {
            throw new DropdownNotFoundException(OPTION_NULL_MESSAGE);
        }
        return dropdownComponent;
    }

    private void createDropdownComponent(String cssSearchLocator) {
        dropdownComponent = new DropdownComponent(cssSearchLocator);
    }

    private void clickDropdownComponentByPartialName(String optionName) {
        List<String> optionsText = dropdownComponent.getListOptionsText();
        boolean optionExists = optionsText.stream()
                .anyMatch(option -> option.contains(optionName));

        if (!optionExists) {
            throw new DropdownNotFoundException(String.format(OPTION_NOT_FOUND_MESSAGE, optionName, optionsText));
        }

        dropdownComponent.clickDropdownOptionByPartialName(optionName);
        closeDropdownComponent();
    }

    private void closeDropdownComponent() {
        clickQubStudioLabel();
        dropdownComponent = null;
    }

    protected GuestDropdown getDropdownGuest() {
        if (dropdownGuest == null) {
            throw new DropdownNotFoundException(OPTION_NULL_MESSAGE);
        }
        return dropdownGuest;
    }

    private GuestDropdown createDropdownGuest() {
        dropdownGuest = new GuestDropdown();
        return getDropdownGuest();
    }

    private void clickDropdownGuestRegister() {
        getDropdownGuest().clickRegister();
        dropdownGuest = null;
    }

    private void clickDropdownGuestLogin() {
        getDropdownGuest().clickLogin();
        dropdownGuest = null;
    }

    private void closeDropdownGuest() {
        clickQubStudioLabel();
        dropdownGuest = null;
    }

    protected LoggedDropdown getDropdownLogged() {
        if (dropdownLogged == null) {
            throw new DropdownNotFoundException(OPTION_NULL_MESSAGE);
        }
        return dropdownLogged;
    }

    private void createDropdownLogged() {
        dropdownLogged = new LoggedDropdown();
    }

    private void clickDropdownLoggedAddClub() {
        getDropdownLogged().clickAddClubLink();
        dropdownLogged = null;
    }

    private void clickDropdownLoggedAddCenter() {
        getDropdownLogged().clickAddCenterLink();
        dropdownLogged = null;
    }

    private void clickDropdownLoggedSearchCertificates() {
        getDropdownLogged().clickSearchCertificatesLink();
        dropdownLogged = null;
    }

    private void clickDropdownLoggedPersonalProfile() {
        getDropdownLogged().clickPersonalProfileLink();
        dropdownLogged = null;
    }

    private void clickDropdownLoggedSignout() {
        getDropdownLogged().clickSignOutLink();
        dropdownLogged = null;
    }

    private void closeDropdownLogged() {
        clickQubStudioLabel();
        dropdownLogged = null;
    }

    protected void openCaretDropdown() {
        clickQubStudioLabel();
        clickCaretDropdownLink();
    }

    private void openCityDropdownComponent() {
        clickQubStudioLabel();
        clickCityDropdownLink();
        createDropdownComponent(LIST_CITY_CSS);
    }

    private void openChallengeDropdownComponent() {
        clickQubStudioLabel();
        clickChallengeLink();
        createDropdownComponent(LIST_CHALLENGE_CSS);
    }

    public <T extends ChallengePage> T chooseChallenge(String challengeName, Class<T> clazz) {
        openChallengeDropdownComponent();
        clickDropdownComponentByPartialName(challengeName);
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            logger.error("Error creating instance of " + clazz.getName() + ": " + e.getMessage());
            throw new ChallengeSelectionException(clazz.getName(), e);
        }
    }

    public String getPopupMessageLabelText() {
        new WebDriverWait(DriverUtils.getInstance().getDriver(), Duration.ofSeconds(10)).until((ExpectedCondition<Boolean>) driver -> !search.cssSelector(POPUP_MESSAGE_CSS).getText().isEmpty());

        List<WebElement> popupMessageLabel = search.cssSelectors(POPUP_MESSAGE_CSS);
        logger.warn("\tpopupMessageLabel.size() = " + popupMessageLabel.size());
        logger.warn("\tpopupMessageLabel.get(0).getText() = " + popupMessageLabel.get(0).getText());
        if (popupMessageLabel.isEmpty()) {
            return "";
        }
        return popupMessageLabel.get(0).getText();
    }

    public boolean isUserLogged() {
        String userProfilePicSrc = getUserProfilePic().getAttribute(TAG_ATTRIBUTE_CLASS);
        return userProfilePicSrc.contains("avatarIfLogin");
    }

    protected void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) DriverUtils.getInstance().getDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public HomePage gotoHomePage() {
        clickHomeLink();
        return new HomePage();
    }

    public ClubPage gotoClubPage() {
        clickClubLink();
        return new ClubPage();
    }

    public ChallengeTeachPage gotoTeachChallengePage() {
        openChallengeDropdownComponent();
        clickDropdownComponentByPartialName("Навчайся");
        return new ChallengeTeachPage();
    }

    public <T extends ChallengePage> T gotoChallengePage(String challengeName, Class<T> clazz) {
        return chooseChallenge(challengeName, clazz);
    }

    public <T extends ChallengePage> T gotoChallengePage(Challenges challengeName, Class<T> clazz) {
        return chooseChallenge(challengeName.getName(), clazz);
    }

    public ClubPage chooseCity(String cityName) {
        openCityDropdownComponent();
        try { // TODO remove sleep
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickDropdownComponentByPartialName(cityName);
        return new ClubPage();
    }

    public NewsPage gotoNewsPage() {
        clickNewsLink();
        return new NewsPage();
    }

    public AboutUsPage gotoAboutUsPage() {
        clickAboutUsLink();
        return new AboutUsPage();
    }

    public UkrainianServicePage gotoUkrainianServicePage() {
        clickUkrainianServiceLink();
        return new UkrainianServicePage();
    }

    public LoginModal openLoginModal() {
        openCaretDropdown();
        createDropdownGuest();
        clickDropdownGuestLogin();
        return new LoginModal();
    }

    public HomePage signOutUser() {
        openCaretDropdown();
        createDropdownLogged();
        clickDropdownLoggedSignout();
        return new HomePage();
    }
}
