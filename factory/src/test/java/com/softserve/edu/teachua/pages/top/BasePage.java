package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.data.Challenges;
import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.component.dropdown.ChallengeDropdownComponent;
import com.softserve.edu.teachua.pages.main.AboutUsPage;
import com.softserve.edu.teachua.pages.main.ClubsPage;
import com.softserve.edu.teachua.pages.main.HelpUsPage;
import com.softserve.edu.teachua.pages.main.HomePage;
import com.softserve.edu.teachua.pages.main.NewsPage;
import com.softserve.edu.teachua.pages.main.UkrainianServicesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BasePage<T extends BasePage<T>> extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.logo")
    private WebElement logo;

    @FindBy(css = "button.flooded-button.donate-button")
    private WebElement donateButton;

    @FindBy(xpath = "//span[@class='ant-menu-title-content']//a[@href='/dev/clubs']")
    private WebElement clubsLink;

    @FindBy(xpath = "//span[@class='ant-menu-title-content']//a[@href='/dev/news']")
    private WebElement newsLink;

    @FindBy(xpath = "//span[@class='ant-menu-title-content']//a[@href='/dev/about']")
    private WebElement aboutLink;

    @FindBy(xpath = "//span[@class='ant-menu-title-content']//a[@href='/dev/service']")
    private WebElement serviceLink;

    public abstract T visit();

    public abstract T assertCurrentURL();

    public void clickLogo() {
        logo.click();
    }

    private void clickDonateButton() {
        donateButton.click();
    }

    private void clickNewsLink() {
        newsLink.click();
    }

    private void clickAboutLink() {
        aboutLink.click();
    }

    private void clickClubsLink() {
        clubsLink.click();
    }

    private void clickServiceLink() {
        serviceLink.click();
    }

    private void switchToTab() {
        getDriver().getWindowHandles()
                .stream()
                .filter(tab -> !tab.equals(getDriver().getWindowHandle()))
                .findFirst()
                .ifPresent(tab -> getDriver().switchTo().window(tab));
    }

    public ClubsPage gotoClubPage() {
        clickClubsLink();
        return new ClubsPage(getDriver());
    }

    public HelpUsPage gotoHelpUsPage() {
        scrollTo(donateButton);
        clickDonateButton();
        switchToTab();
        return new HelpUsPage(getDriver());
    }

    public NewsPage gotoNewsPage() {
        clickNewsLink();
        return new NewsPage(getDriver());
    }

    public AboutUsPage gotoAboutUsPage() {
        clickAboutLink();
        return new AboutUsPage(getDriver());
    }

    public UkrainianServicesPage gotoUkrainianServicePage() {
        clickServiceLink();
        return new UkrainianServicesPage(getDriver());
    }

    public HomePage gotoHomePage() {
        clickLogo();
        return new HomePage(getDriver());
    }

    public ChallengePage gotoChallengePage(Challenges challenge) {
        return new ChallengeDropdownComponent(getDriver())
                .createChallengePage(challenge.getPageClass(), challenge.getName());
    }
}
