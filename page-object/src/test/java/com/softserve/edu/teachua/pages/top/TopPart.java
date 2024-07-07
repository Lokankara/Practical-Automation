package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.exception.ChallengeSelectionException;
import com.softserve.edu.teachua.exception.DropdownNotFoundException;
import com.softserve.edu.teachua.exception.DropdownNotInitializedException;
import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.challenge.ChallengeTeachPage;
import com.softserve.edu.teachua.pages.club.ClubPage;
import com.softserve.edu.teachua.pages.menu.AboutUsPage;
import com.softserve.edu.teachua.pages.menu.HomePage;
import com.softserve.edu.teachua.pages.menu.NewsPage;
import com.softserve.edu.teachua.pages.menu.UkrainianServicePage;
import com.softserve.edu.teachua.pages.top.component.DropdownComponent;
import com.softserve.edu.teachua.pages.user.GuestDropdown;
import com.softserve.edu.teachua.pages.user.LoggedDropdown;
import com.softserve.edu.teachua.pages.user.LoginModal;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class TopPart extends Waiter {
    public static final String TAG_ATTRIBUTE_SRC = "src";
    public static final String TAG_ATTRIBUTE_VALUE = "value";
    public static final String OPTION_NOT_FOUND_MESSAGE = "Option %s not found in %s";
    protected static final String OPTION_NULL_MESSAGE = "Dropdown is null";
    protected static final String OPTION_NAME = "Навчайся";
    protected static final String TITLE = "Навчай українською";
    protected static final String PAGE_DO_NOT_EXIST = "Page do not exist.";
    protected static final By LIST_CHALLENGE = By.cssSelector("a[href*='/challenges']");

    private WebElement homeLink;
    private WebElement clubLink;
    private WebElement challengeLink;
    private WebElement newsLink;
    private WebElement aboutUsLink;
    private WebElement ukrainianServiceLink;
    private WebElement caretDropdownLink;
    private WebElement qubStudioLabel;
    private DropdownComponent dropdownComponent;
    private GuestDropdown dropdownGuest;
    private LoggedDropdown dropdownLogged;

    public TopPart(WebDriver driver) {
        super(driver);
        initElements();
        checkBrowser();
    }

    protected void checkBrowser() {
        assertTitle();
        assertCurrentURL();
    }

    private void assertTitle() {
        Assertions.assertEquals(TITLE, driver.getTitle());
    }

    protected abstract void assertCurrentURL();

    private void initElements() {
        homeLink = driver.findElement(By.cssSelector("div.left-side-menu > a"));
        clubLink = driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']"));
        challengeLink = driver.findElement(By.cssSelector("span.challenge-text"));
        newsLink = driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/news']"));
        aboutUsLink = driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/about']"));
        ukrainianServiceLink = driver.findElement(By.cssSelector("span.ant-menu-title-content > a[href*='/service']"));
        caretDropdownLink = driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down"));
        qubStudioLabel = driver.findElement(By.cssSelector("div.qubstudio"));
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
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

    public WebElement getCaretDropdownLink() {
        return caretDropdownLink;
    }

    public void clickCaretDropdownLink() {
        getCaretDropdownLink().click();
    }

    protected DropdownComponent getDropdownComponent() {
        if (dropdownComponent == null) {
            throw new DropdownNotInitializedException(OPTION_NULL_MESSAGE);
        }
        return dropdownComponent;
    }

    private void createDropdownComponent(By searchLocator) {
        dropdownComponent = new DropdownComponent(driver, searchLocator);
    }

    private void clickDropdownComponentByPartialName(String optionName) {
        if (!getDropdownComponent().isExistDropdownOptionByPartialName(optionName)) {
            throw new DropdownNotFoundException(
                    String.format(OPTION_NOT_FOUND_MESSAGE, optionName,
                            getDropdownComponent().getListOptionsText().toString()));
        }
        getDropdownComponent().clickDropdownOptionByPartialName(optionName);
        dropdownComponent = null;
        //closeDropdownComponent();
    }

    private void closeDropdownComponent() {
        clickQubStudioLabel();
        dropdownComponent = null;
    }

    protected GuestDropdown getDropdownGuest() {
        if (dropdownGuest == null) {
            throw new DropdownNotInitializedException(OPTION_NULL_MESSAGE);
        }
        return dropdownGuest;
    }

    private GuestDropdown createDropdownGuest() {
        dropdownGuest = new GuestDropdown(driver);
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
            throw new DropdownNotInitializedException(OPTION_NULL_MESSAGE);
        }
        return dropdownLogged;
    }

    private LoggedDropdown createDropdownLogged() {
        dropdownLogged = new LoggedDropdown(driver);
        return getDropdownLogged();
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

    private void clickDropdownLoggedSignOut() {
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

    private void openDropdownComponent() {
        clickQubStudioLabel();
        clickChallengeLink();
        createDropdownComponent(LIST_CHALLENGE);
    }

    private <T extends ChallengePage> T chooseChallenge(Challenges challenge, Class<T> clazz) {
        openDropdownComponent();
        clickDropdownComponentByPartialName(challenge.getName());

        T pageObject;
        try {
            Constructor<T> ctor = clazz.getConstructor(WebDriver.class);
            pageObject = ctor.newInstance(driver);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new ChallengeSelectionException(PAGE_DO_NOT_EXIST + clazz.getName(), e);
        }

        return pageObject;
    }

    public HomePage gotoHomePage() {
        clickHomeLink();
        return new HomePage(driver);
    }

    public ClubPage gotoClubPage() {
        clickClubLink();
        return new ClubPage(driver);
    }

    public ChallengeTeachPage gotoTeachChallengePage() {
        openDropdownComponent();
        clickDropdownComponentByPartialName(OPTION_NAME);
        return new ChallengeTeachPage(driver);
    }

    public <T extends ChallengePage> T gotoChallengePage(Challenges challenge, Class<T> clazz) {
        return chooseChallenge(challenge, clazz);
    }

    public NewsPage gotoNewsPage() {
        clickNewsLink();
        return new NewsPage(driver);
    }

    public AboutUsPage gotoAboutUsPage() {
        clickAboutUsLink();
        return new AboutUsPage(driver);
    }

    public UkrainianServicePage gotoUkrainianServicePage() {
        clickUkrainianServiceLink();
        return new UkrainianServicePage(driver);
    }

    public LoginModal openLoginModal() {
        openCaretDropdown();
        createDropdownGuest();
        clickDropdownGuestLogin();
        return new LoginModal(driver);
    }

    public HomePage signOutUser() {
        openCaretDropdown();
        createDropdownLogged();
        clickDropdownLoggedSignOut();
        return new HomePage(driver);
    }
}
