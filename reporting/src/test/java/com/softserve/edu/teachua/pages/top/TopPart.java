package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.data.Challengies;
import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.pages.menu.AboutUsPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.menu.NewsPage;
import com.softserve.edu.teachua.pages.menu.UkrainianServicePage;
import com.softserve.edu.teachua.pages.user.GuestDropdown;
import com.softserve.edu.teachua.pages.user.LoggedDropdown;
import com.softserve.edu.teachua.pages.user.LoginModal;
import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.browser.DriverUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class TopPart {
    public static final String POPUP_MESSAGE_SUCCESSFULLY = "Ви успішно залогувалися!";
    public static final String TAG_ATTRIBUTE_VALUE = "value";
    public static final String TAG_ATTRIBUTE_CLASS = "class";
    public static final String TAG_ATTRIBUTE_HREF = "href";
    public static final String OPTION_NOT_FOUND_MESSAGE = "Option %s not found in %s";
    protected final String OPTION_NULL_MESSAGE = "Dropdown is null";
    public static final String POPUP_MESSAGE_CSSSELECTOR = "div.ant-message-notice-wrapper span:last-child";
    protected final String LIST_CHALLENGE_CSSSELECTOR = "a[href*='/challenges']";
    protected final String LIST_CITY_CSSSELECTOR = "ul.ant-dropdown-menu span";

    protected Search search;
    private WebElement homeLink;
    private WebElement clubLink;
    private WebElement challengeLink;
    private WebElement newsLink;
    private WebElement aboutUsLink;
    private WebElement ukrainianServiceLink;
    private WebElement cityDropdownLink;
    private WebElement caretDropdownLink;
    private WebElement qubStudioLabel;
    private WebElement userProfilePic;
    private DropdownComponent dropdownComponent;
    private GuestDropdown dropdownGuest;
    private LoggedDropdown dropdownLogged;

    public TopPart() {
        ReportUtils.logInfo("Initializing TopPart with search");
        search = SearchStrategy.getSearch();
        initElements();
    }

    private void initElements() {
        homeLink = search.cssSelector("div.left-side-menu > a");
        clubLink = search.cssSelector("span.ant-menu-title-content > a[href*='/clubs']");
        challengeLink = search.cssSelector("span.challenge-text");
        newsLink = search.cssSelector("span.ant-menu-title-content > a[href*='/news']");
        aboutUsLink = search.cssSelector("span.ant-menu-title-content > a[href*='/about']");
        ukrainianServiceLink = search.cssSelector("span.ant-menu-title-content > a[href*='/service']");
        cityDropdownLink = search.cssSelector("div.city span.anticon-caret-down");
        caretDropdownLink = search.cssSelector("div.user-profile span.anticon.anticon-caret-down");
        qubStudioLabel = search.cssSelector("div.qubstudio");
        userProfilePic = search.cssSelector("div.user-profile span.ant-avatar");
    }

    public WebElement getHomeLink() {
        ReportUtils.logInfo("Getting home link element.");
        return homeLink;
    }

    public String getHomeLinkText() {
        String text = getHomeLink().getText();
        ReportUtils.logInfo("Getting text from home link: " + text);
        return text;
    }

    public void clickHomeLink() {
        ReportUtils.logAction("Clicking home link.");
        getHomeLink().click();
    }

    public WebElement getClubLink() {
        ReportUtils.logInfo("Got club link element");
        return clubLink;
    }

    public String getClubLinkText() {
        String text = getClubLink().getText();
        ReportUtils.logInfo("Got text from club link: " + text);
        return text;
    }

    public void clickClubLink() {
        ReportUtils.logAction("Clicking on Club link");
        getClubLink().click();
    }

    public WebElement getChallengeLink() {
        ReportUtils.logInfo("Got ChallengeLink element");
        return challengeLink;
    }

    public String getChallengeLinkText() {
        String text = getChallengeLink().getText();
        ReportUtils.logInfo("Got text from challenge link: " + text);
        return text;
    }

    public void clickChallengeLink() {
        ReportUtils.logAction("Clicking on Challenge link");
        getChallengeLink().click();
    }

    public WebElement getNewsLink() {
        ReportUtils.logAction("Get News Link element");
        return newsLink;
    }

    public String getNewsLinkText() {
        String text = getNewsLink().getText();
        ReportUtils.logInfo("Got news link text: " + text);
        return text;
    }

    public void clickNewsLink() {
        ReportUtils.logAction("Clicking on News link");
        getNewsLink().click();
    }

    public WebElement getAboutUsLink() {
        ReportUtils.logInfo("Getting About Us link element");
        return aboutUsLink;
    }

    public String getAboutUsLinkText() {
        String text = getAboutUsLink().getText();
        ReportUtils.logInfo("Got About Us link text: " + text);
        return text;
    }

    public void clickAboutUsLink() {
        ReportUtils.logAction("Clicking on About Us link");
        getAboutUsLink().click();
    }

    public WebElement getUkrainianServiceLink() {
        ReportUtils.logInfo("Getting Ukrainian Service link element");
        return ukrainianServiceLink;
    }

    public String getUkrainianServiceLinkText() {
        String text = getUkrainianServiceLink().getText();
        ReportUtils.logInfo("Got Ukrainian Service link text: " + text);
        return text;
    }

    public void clickUkrainianServiceLink() {
        ReportUtils.logAction("Clicking on Ukrainian Service link");
        getUkrainianServiceLink().click();
    }

    public WebElement getQubStudioLabel() {
        ReportUtils.logInfo("Getting Qub Studio label element");
        return qubStudioLabel;
    }

    public String getQubStudioLabelText() {
        String text = getQubStudioLabel().getText();
        ReportUtils.logInfo("Got Qub Studio label text: " + text);
        return text;
    }

    public void clickQubStudioLabel() {
        ReportUtils.logAction("Clicking on Qub Studio label");
        getQubStudioLabel().click();
    }

    public WebElement getCityDropdownLink() {
        ReportUtils.logInfo("Getting City dropdown link element");
        return cityDropdownLink;
    }

    public void clickCityDropdownLink() {
        ReportUtils.logAction("Clicking on City dropdown link");
        getCityDropdownLink().click();
    }

    public WebElement getCaretDropdownLink() {
        ReportUtils.logInfo("Getting Caret dropdown link element");
        return caretDropdownLink;
    }

    public void clickCaretDropdownLink() {
        ReportUtils.logAction("Clicking on Caret dropdown link");
        getCaretDropdownLink().click();
    }

    public WebElement getUserProfilePic() {
        ReportUtils.logInfo("Getting User profile picture element");
        return userProfilePic;
    }

    protected DropdownComponent getDropdownComponent() {
        if (dropdownComponent == null) {
            ReportUtils.logError(OPTION_NULL_MESSAGE);
            throw new RuntimeException(OPTION_NULL_MESSAGE);
        }
        ReportUtils.logInfo("Getting Dropdown Component");
        return dropdownComponent;
    }

    private DropdownComponent createDropdownComponent(By searchLocator) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            ReportUtils.logError(e.getMessage());
            throw new RuntimeException(e);
        }
        dropdownComponent = new DropdownComponent(searchLocator);
        ReportUtils.logAction("create Dropdown Component");
        return getDropdownComponent();
    }

    private void clickDropdownComponentByPartialName(String optionName) {
        if (!getDropdownComponent().isExistDropdownOptionByPartialName(optionName)) {
            String error = String.format(OPTION_NOT_FOUND_MESSAGE, optionName,
                    getDropdownComponent().getListOptionsText().toString());
            ReportUtils.logError(error);
            throw new RuntimeException(error);
        }
        getDropdownComponent().clickDropdownOptionByPartialName(optionName);
        ReportUtils.logAction("click Dropdown Option By Partial Name: " + optionName);
        dropdownComponent = null;

    }

    private void closeDropdownComponent() {
        clickQubStudioLabel();
        ReportUtils.logAction("Closing dropdown component");
        dropdownComponent = null;
    }

    protected GuestDropdown getDropdownGuest() {
        if (dropdownGuest == null) {
            ReportUtils.logError(OPTION_NULL_MESSAGE);
            throw new RuntimeException(OPTION_NULL_MESSAGE);
        }
        ReportUtils.logInfo("get Dropdown Guest");
        return dropdownGuest;
    }

    private GuestDropdown createDropdownGuest() {
        dropdownGuest = new GuestDropdown();
        ReportUtils.logAction("create Guest Dropdown");
        return getDropdownGuest();
    }

    private void clickDropdownGuestRegister() {
        getDropdownGuest().clickRegister();
        ReportUtils.logAction("Clicking on Guest Register link");
        dropdownGuest = null;
    }

    private void clickDropdownGuestLogin() {
        getDropdownGuest().clickLogin();
        ReportUtils.logAction("Clicking on Guest Login link");
        dropdownGuest = null;
    }

    private void closeDropdownGuest() {
        clickQubStudioLabel();
        ReportUtils.logAction("Closing guest dropdown");
        dropdownGuest = null;
    }

    protected LoggedDropdown getDropdownLogged() {
        if (dropdownLogged == null) {
            ReportUtils.logError(OPTION_NULL_MESSAGE);
            throw new RuntimeException(OPTION_NULL_MESSAGE);
        }
        ReportUtils.logInfo("get Dropdown Logged");
        return dropdownLogged;
    }

    private LoggedDropdown createDropdownLogged() {
        dropdownLogged = new LoggedDropdown();
        ReportUtils.logAction("Created Dropdown Logged");
        return getDropdownLogged();
    }

    private void clickDropdownLoggedAddClub() {
        getDropdownLogged().clickAddClubLink();
        ReportUtils.logAction("Clicking on Dropdown Logged AddClub");
        dropdownLogged = null;
    }

    private void clickDropdownLoggedAddCenter() {
        getDropdownLogged().clickAddCenterLink();
        ReportUtils.logAction("Clicking on Dropdown Logged AddCenter");
        dropdownLogged = null;
    }

    private void clickDropdownLoggedSearchCertificates() {
        getDropdownLogged().clickSearchCertificatesLink();
        ReportUtils.logAction("clicking Dropdown Logged Search Certificates");
        dropdownLogged = null;
    }

    private void clickDropdownLoggedPersonalProfile() {
        getDropdownLogged().clickPersonalProfileLink();
        ReportUtils.logAction("clicking Dropdown Logged Personal Profile");
        dropdownLogged = null;
    }

    private void clickDropdownLoggedSignout() {
        getDropdownLogged().clickSignOutLink();
        ReportUtils.logAction("clicking Dropdown Logged Sign Out");
        dropdownLogged = null;
    }

    private void closeDropdownLogged() {
        clickQubStudioLabel();
        ReportUtils.logAction("Closing logged dropdown");
        dropdownLogged = null;
    }

    protected void openCaretDropdown() {
        ReportUtils.logAction("Opening caret dropdown");
        clickQubStudioLabel();
        clickCaretDropdownLink();
    }

    private void openCityDropdownComponent() {
        ReportUtils.logAction("Opening city dropdown component");
        clickQubStudioLabel();
        clickCityDropdownLink();
        createDropdownComponent(By.cssSelector(LIST_CITY_CSSSELECTOR));
    }

    private void openChallengeDropdownComponent() {
        ReportUtils.logAction("Opening challenge dropdown component");
        clickQubStudioLabel();
        clickChallengeLink();
        createDropdownComponent(By.cssSelector(LIST_CHALLENGE_CSSSELECTOR));
    }

    private <T> T chooseChallenge(String challengeName, Class<T> clazz) {
        openChallengeDropdownComponent();
        clickDropdownComponentByPartialName(challengeName);
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            ReportUtils.logInfo("Created instance of " + clazz.getSimpleName());
            return instance;
        } catch (Exception e) {
            ReportUtils.logError("Failed to create instance of " + clazz.getSimpleName() + " " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public String getPopupMessageLabelText() {
        search = SearchStrategy.setExplicitExistText();
        search.isLocatedCss(TopPart.POPUP_MESSAGE_CSSSELECTOR);
        search = SearchStrategy.restoreStrategy();
        List<WebElement> popupMessageLabel = search.cssSelectors(POPUP_MESSAGE_CSSSELECTOR);
        if (popupMessageLabel.isEmpty()) {
            ReportUtils.logInfo("Popup message label not found");
            return "";
        }

        String labelText = popupMessageLabel.get(0).getText();
        ReportUtils.logInfo("Got popup message label text: " + labelText);
        return labelText;
    }

    public boolean isUserLogged() {
        ReportUtils.logAction("Checking if user is logged in");
        String userProfilePicSrc = getUserProfilePic().getAttribute(TAG_ATTRIBUTE_CLASS);
        boolean isLogged = userProfilePicSrc.contains("avatarIfLogin");
        ReportUtils.logInfo("User is logged in: " + isLogged);
        return isLogged;
    }

    protected void scrollToElement(WebElement webElement) {
        ReportUtils.logAction("Scrolling to element");
        DriverUtils.scrollToElement(webElement);
    }

    public HomePage gotoHomePage() {
        ReportUtils.logAction("Navigating to Home page");
        clickHomeLink();
        return new HomePage();
    }

    public ClubPage gotoClubPage() {
        ReportUtils.logAction("Navigating to Club page");
        clickClubLink();
        return new ClubPage();
    }

    public ChallengeTeachPage gotoTeachChallengePage() {
        ReportUtils.logAction("Navigating to Teach Challenge page");
        openChallengeDropdownComponent();
        clickDropdownComponentByPartialName("Навчайся");
        return new ChallengeTeachPage();
    }

    public <T> T gotoChallengePage(String challengeName, Class<T> clazz) {
        ReportUtils.logAction("Navigating to Challenge page: " + challengeName);
        return chooseChallenge(challengeName, clazz);
    }

    public <T> T gotoChallengePage(Challengies challengeName, Class<T> clazz) {
        ReportUtils.logAction("Navigating to Challenge page: " + challengeName.getName());
        return chooseChallenge(challengeName.getName(), clazz);
    }

    public ClubPage chooseCity(Cities city) {
        ReportUtils.logAction("Choosing city: " + city.getCity());
        openCityDropdownComponent();
        clickDropdownComponentByPartialName(city.getCity());
        return new ClubPage();
    }

    public NewsPage gotoNewsPage() {
        ReportUtils.logAction("Navigating to News page");
        clickNewsLink();
        return new NewsPage();
    }

    public AboutUsPage gotoAboutUsPage() {
        ReportUtils.logAction("Navigating to About Us page");
        clickAboutUsLink();
        return new AboutUsPage();
    }

    public UkrainianServicePage gotoUkrainianServicePage() {
        ReportUtils.logAction("Navigating to Ukrainian Service page");
        clickUkrainianServiceLink();
        return new UkrainianServicePage();
    }

    public LoginModal openLoginModal() {
        ReportUtils.logAction("Opening login modal");
        openCaretDropdown();
        createDropdownGuest();
        clickDropdownGuestLogin();
        return new LoginModal();
    }

    public HomePage signOutUser() {
        ReportUtils.logAction("Signing out user");
        openCaretDropdown();
        createDropdownLogged();
        clickDropdownLoggedSignout();
        return new HomePage();
    }
}
