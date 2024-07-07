package com.softserve.edu.teachua.pages.part;

import com.softserve.edu.teachua.exception.DropdownNotInitializedException;
import com.softserve.edu.teachua.pages.component.CitiesDropdownComponent;
import com.softserve.edu.teachua.pages.component.GuestDropdown;
import com.softserve.edu.teachua.pages.component.LoggedDropdown;
import com.softserve.edu.teachua.pages.modal.AddCenterModal;
import com.softserve.edu.teachua.pages.modal.AddClubModal;
import com.softserve.edu.teachua.pages.modal.LoginModal;
import com.softserve.edu.teachua.pages.page.AboutUsPage;
import com.softserve.edu.teachua.pages.page.HomePage;
import com.softserve.edu.teachua.pages.page.NewsPage;
import com.softserve.edu.teachua.pages.page.UkrainianServicePage;
import com.softserve.edu.teachua.pages.page.club.ClubPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public abstract class TopPart {

    protected WebDriver driver;
    private WebElement homeLink;
    private WebElement clubLink;
    private WebElement challengeLink;
    private WebElement newsLink;
    private WebElement aboutUsLink;
    private WebElement ukrainianServiceLink;
    private WebElement caretDropdownLink;
    private WebElement qubStudioLabel;
    private WebElement dropdownOpenCity;
    private GuestDropdown dropdownGuest;
    private LoggedDropdown dropdownLogged;
    private CitiesDropdownComponent dropdownCities;
    private static final By QUB_STUDIO = By.cssSelector("div.qubstudio");
    private static final By CHALLENGE_TITLE = By.cssSelector("span.challenge-text");
    private static final By LEFT_SIDE_MENU = By.cssSelector("div.left-side-menu > a");
    private static final By TRIGGER_CITY = By.cssSelector("div.ant-dropdown-trigger.city");
    private static final By NEWS_TITLE = By.cssSelector("span.ant-menu-title-content > a[href*='/news']");
    private static final By CLUB_TITLE = By.cssSelector("span.ant-menu-title-content > a[href*='/clubs']");
    private static final By ABOUT_TITLE = By.cssSelector("span.ant-menu-title-content > a[href*='/about']");
    private static final By USER_PROFILE = By.cssSelector("div.user-profile span.anticon.anticon-caret-down");
    private static final By SERVICE_TITLE = By.cssSelector("span.ant-menu-title-content > a[href*='/service']");
    protected static final Duration EXPLICITLY_WAIT = Duration.ofSeconds(10L);
    protected static final Duration IMPLICITLY_WAIT = Duration.ofSeconds(5L);
    protected static final String OPTION_NULL_MESSAGE = "Dropdown is null";
    protected static final String TAG_ATTRIBUTE_VALUE = "value";

    public TopPart(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        initElements();
    }

    private void initElements() {
        homeLink = getElement(LEFT_SIDE_MENU);
        clubLink = getElement(CLUB_TITLE);
        challengeLink = getElement(CHALLENGE_TITLE);
        newsLink = getElement(NEWS_TITLE);
        aboutUsLink = getElement(ABOUT_TITLE);
        ukrainianServiceLink = getElement(SERVICE_TITLE);
        caretDropdownLink = getElement(USER_PROFILE);
        qubStudioLabel = getElement(QUB_STUDIO);
        dropdownOpenCity = getElement(TRIGGER_CITY);
    }

    private CitiesDropdownComponent getDropdownCities() {
        return dropdownCities;
    }

    private WebElement getDropdownOpenCity() {return dropdownOpenCity;}

    protected void clickDropdownCity() {
        getDropdownOpenCity().click();
    }

    protected WebElement getHomeLink() {
        return homeLink;
    }

    protected String getHomeLinkText() {
        return getHomeLink().getText();
    }

    protected void clickHomeLink() {
        getHomeLink().click();
    }

    protected WebElement getClubLink() {
        return clubLink;
    }

    protected String getClubLinkText() {
        return getClubLink().getText();
    }

    protected void clickClubLink() {
        getClubLink().click();
    }

    protected WebElement getChallengeLink() {
        return challengeLink;
    }

    protected String getChallengeLinkText() {
        return getChallengeLink().getText();
    }

    protected void clickChallengeLink() {
        getChallengeLink().click();
    }

    protected WebElement getNewsLink() {
        return newsLink;
    }

    protected String getNewsLinkText() {
        return getNewsLink().getText();
    }

    protected void clickNewsLink() {
        getNewsLink().click();
    }

    protected WebElement getAboutUsLink() {
        return aboutUsLink;
    }

    protected String getAboutUsLinkText() {
        return getAboutUsLink().getText();
    }

    protected void clickAboutUsLink() {
        getAboutUsLink().click();
    }

    protected WebElement getUkrainianServiceLink() {
        return ukrainianServiceLink;
    }

    protected String getUkrainianServiceLinkText() {
        return getUkrainianServiceLink().getText();
    }

    protected void clickUkrainianServiceLink() {
        getUkrainianServiceLink().click();
    }

    protected WebElement getQubStudioLabel() {
        return qubStudioLabel;
    }

    protected String getQubStudioLabelText() {
        return getQubStudioLabel().getText();
    }

    protected void clickQubStudioLabel() {
        getQubStudioLabel().click();
    }

    protected WebElement getCaretDropdownLink() {
        return caretDropdownLink;
    }

    protected void clickCaretDropdownLink() {
        getCaretDropdownLink().click();
        caretDropdownLink = null;
    }

    protected GuestDropdown getDropdownGuest() {
        if (dropdownGuest == null) {
            throw new DropdownNotInitializedException(OPTION_NULL_MESSAGE, "DropdownGuest");
        }
        return dropdownGuest;
    }

    protected CitiesDropdownComponent openDropdownCity(){
        clickDropdownCity();
        return createDropdownCity();
    }

    private CitiesDropdownComponent createDropdownCity() {
        dropdownCities = new CitiesDropdownComponent(driver);
        return getDropdownCities();
    }

    private GuestDropdown createDropdownGuest() {
        dropdownGuest = new GuestDropdown(driver);
        return getDropdownGuest();
    }

    private void clickDropdownGuestRegister() {
        getDropdownGuest().clickRegister();
        dropdownGuest = null;
    }

    private void closeDropdownGuest() {
        clickQubStudioLabel();
        dropdownGuest = null;
    }

    private LoggedDropdown getDropdownLogged() {
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
        createDropdownLogged().clickAddClubLink();
    }

    private void clickDropdownLoggedAddCenter() {
        createDropdownLogged().clickAddCenterLink();
    }

    private void clickDropdownLoggedSearchCertificates() {
        getDropdownLogged().clickSearchCertificatesLink();
        dropdownLogged = null;
    }

    private void clickDropdownLoggedPersonalProfile() {
        getDropdownLogged().clickPersonalProfileLink();
        dropdownLogged = null;
    }

    private void closeDropdownLogged() {
        clickQubStudioLabel();
        dropdownLogged = null;
    }

    public ClubPage selectCityClubs(String city) {

        return openDropdownCity().selectCity(city);
    }

    protected void openCaretDropdown() {
        clickQubStudioLabel();
        clickCaretDropdownLink();
    }

    protected void scrollToElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public LoggedDropdown openLoggedDropDown() {
        clickCaretDropdownLink();
        return new LoggedDropdown(driver);
    }

    public HomePage gotoHomePage() {
        clickHomeLink();
        return new HomePage(driver);
    }

    public ClubPage gotoClubPage() {
        clickClubLink();
        return new ClubPage(driver);
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
        createDropdownGuest().clickDropdownGuestLogin();
        dropdownGuest = null;
        return new LoginModal(driver);
    }

    public HomePage signOutUser() {
        createDropdownLogged().clickDropdownLoggedSignOut();
        dropdownLogged = null;
        return new HomePage(driver);
    }

    public AddClubModal openDropdownLoggedAddClubs() {
        clickDropdownLoggedAddClub();
        return getDropdownLogged().getAddClubModal();
    }

    public AddCenterModal openDropdownLoggedAddCenterModal() {
        clickDropdownLoggedAddCenter();
        return getDropdownLogged().getAddCenterModal();
    }

    protected WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement getVisibleElement(By locator) {
        return applyWait(locator, ExpectedConditions::visibilityOfElementLocated);
    }

    protected WebElement getClickableElement(By locator) {
        return applyWait(locator, ExpectedConditions::elementToBeClickable);
    }

    protected List<WebElement> getCommentsUntilExpectedSize(By locator, int size) {
        return applyWait(wait -> wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, size)));
    }

    protected List<WebElement> getVisibleElements(By locator) {
        return applyWait(wait -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
    }

    protected WebElement applyWait(By locator, Function<By, ExpectedCondition<WebElement>> condition) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebElement element = getWait().until(condition.apply(locator));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        return element;
    }

    private List<WebElement> applyWait(Function<WebDriverWait, List<WebElement>> condition) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        List<WebElement> elements = condition.apply(getWait());
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        return elements;
    }

    protected void getElementsToBeNot(int initialClubSize, By locator) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.numberOfElementsToBe(locator, initialClubSize)));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, EXPLICITLY_WAIT);
    }
}
