package com.softserve.edu.bootstrap;

import com.softserve.edu.teachua.pages.top.BaseModel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BootstrapFormControlsPage extends BaseModel {

    @FindBy(id = "exampleFormControlSelect2")
    private WebElement multi;
    @FindBy(id = "exampleFormControlSelect1")
    private WebElement single;

    private static final String script = "return arguments[0].selectedOptions[0].textContent;";

    public BootstrapFormControlsPage(WebDriver driver) {
        super(driver);
    }

    public void selectOptionByText(String optionText) {
        getSelect().selectByVisibleText(optionText);
    }

    public void selectOptionByIndex(int optionIndex) {
        getSelect().selectByIndex(optionIndex);
    }

    public void selectMultipleOptionsByIndex(List<Integer> optionIndexes) {
        optionIndexes.forEach(getMultiSelect()::selectByIndex);
    }

    public Select getMultiSelect() {
        return new Select(multi);
    }

    public List<String> getSelectedOptionValues() {
        return getMultiSelect().getAllSelectedOptions()
                .stream()
                .map(option -> option.getText().trim())
                .toList();
    }

    public Select getSelect() {
        return new Select(single);
    }

    public String executeScript(String script, Object... args) {
        return (String) ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }

    public String getOptionText() {
        return (executeScript(script, single)).trim();
    }

    public void selectMultipleOptionsByText(List<String> optionTexts) {
        optionTexts.forEach(optionText -> getMultiSelect()
                .getOptions()
                .stream().filter(option -> option.getText().trim().equals(optionText))
                .findFirst()
                .ifPresent(WebElement::click));
    }
}
