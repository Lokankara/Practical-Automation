package com.softserve.edu.teachua.pages.component.dropdown;

import com.softserve.edu.teachua.exception.ChallengeSelectionException;
import com.softserve.edu.teachua.exception.DropdownOptionNotFoundException;
import com.softserve.edu.teachua.pages.challenge.ChallengePage;
import com.softserve.edu.teachua.pages.top.BaseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ChallengeDropdownComponent extends BaseModel {

    @FindBy(css = "a[href*='/challenges']")
    private List<WebElement> listOptions;

    @FindBy(css = "span.challenge-text")
    private WebElement challengeLink;

    public ChallengeDropdownComponent(WebDriver driver) {
        super(driver);
    }

    private void clickChallengeLink() {
        challengeLink.click();
    }

    private void openDropdownComponent() {
        clickChallengeLink();
    }

    private void clickDropdownComponentByPartialName(String optionName) {
        getDropdownOptionByPartialName(optionName).click();
    }

    public WebElement getDropdownOptionByPartialName(String optionName) {
        return listOptions.stream()
                .filter(current -> current.getText().toLowerCase().contains(optionName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new DropdownOptionNotFoundException("Option not found", optionName));
    }

    public <P extends ChallengePage> P createChallengePage(Class<P> clazz, String challengeName) {
        openDropdownComponent();
        clickDropdownComponentByPartialName(challengeName);
        try {
            return clazz.getConstructor(WebDriver.class).newInstance(getDriver());
        } catch (NoSuchMethodException
                 | InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            throw new ChallengeSelectionException(clazz.getName(), e);
        }
    }
}
