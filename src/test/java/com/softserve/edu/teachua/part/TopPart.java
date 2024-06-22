package com.softserve.edu.teachua.part;

import com.softserve.edu.exception.DropdownNotInitializedException;
import com.softserve.edu.teachua.component.DropdownComponent;
import com.softserve.edu.teachua.component.GuestDropdown;
import com.softserve.edu.teachua.component.LoggedDropdown;
import com.softserve.edu.teachua.modal.AddCenterModal;
import com.softserve.edu.teachua.modal.AddClubModal;
import com.softserve.edu.teachua.modal.LoginModal;
import com.softserve.edu.teachua.page.AboutUsPage;
import com.softserve.edu.teachua.page.HomePage;
import com.softserve.edu.teachua.page.NewsPage;
import com.softserve.edu.teachua.page.UkrainianServicePage;
import com.softserve.edu.teachua.page.club.ClubPage;
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

import static com.softserve.edu.teachua.tests.BaseTest.IMPLICITLY_WAIT_SECONDS;

public abstract class TopPart {
    protected static final Duration IMPLICITLY_WAIT = Duration.ofSeconds(5L);
    protected static final Duration EXPLICITLY_WAIT = Duration.ofSeconds(10L);
    protected static final String TAG_ATTRIBUTE_VALUE = "value";
    //protected static final String TAG_ATTRIBUTE_SRC = "src";
    //
    protected final String OPTION_NULL_MESSAGE = "Dropdown is null";
    //protected final String OPTION_NOT_FOUND_MESSAGE = "Option %s not found in %s";
    //protected final String PAGE_DO_NOT_EXIST = "Page do not exist.";
    //
    //protected final String LIST_CURRENCIES_CSSSELECTOR = "div.btn-group.open ul.dropdown-menu li"; // $("form#form-currency li")

    protected WebDriver driver;
    private WebElement homeLink;
    private WebElement clubLink;
    private WebElement challengeLink;
    private WebElement newsLink;
    private WebElement aboutUsLink;
    private WebElement ukrainianServiceLink;
    private WebElement caretDropdownLink;
    private WebElement qubStudioLabel;
    private GuestDropdown dropdownGuest;
    private LoggedDropdown dropdownLogged;
    private DropdownComponent dropdownCities;

    public TopPart(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        initElements();
    }

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

    // Page Object
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

    // caretDropdownLink
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

    // Functional
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

    protected WebElement getElementBy(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> getElementsBy(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement getVisibleElement(By locator) {
        return executeWithTimeout(locator, ExpectedConditions::visibilityOfElementLocated);
    }

    protected WebElement getClickableElement(By locator) {
        return executeWithTimeout(locator, ExpectedConditions::elementToBeClickable);
    }

    protected WebElement executeWithTimeout(
            By locator, Function<By, ExpectedCondition<WebElement>> condition) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        WebElement element = new WebDriverWait(driver, EXPLICITLY_WAIT)
                .until(condition.apply(locator));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        return element;
    }

    protected List<WebElement> getNumberToBeMoreThan(By locator, int size) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        List<WebElement> elements = new WebDriverWait(driver, EXPLICITLY_WAIT)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, size));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT);
        return elements;
    }
}
