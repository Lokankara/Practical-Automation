package com.softserve.edu.page.speak;

import com.softserve.edu.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Header extends BasePage {
    public Header(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@role='menuitem' and contains(@data-menu-id, 'challenge')]")
    private WebElement challengeTab;

    public Header openMenu() {
        challengeTab.click();

        return new Header(getDriver());
    }

    public ChallengePage chooseTab(String menu) {
        getElementByXpath("//a[contains(text(),'" + menu + "')]").click();

        return new ChallengePage(getDriver());
    }
}
