package com.softserve.edu.teachua.pages.top.component;

import com.softserve.edu.teachua.exception.DropdownNotFoundException;
import com.softserve.edu.teachua.pages.top.TopPart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DropdownComponent {

    protected WebDriver driver;

    private List<WebElement> listOptions;

    public DropdownComponent(WebDriver driver, By searchLocator) {
        this.driver = driver;
        initElements(searchLocator);
    }

    private void initElements(By searchLocator) {
        listOptions = driver.findElements(searchLocator);
    }

    public List<WebElement> getListOptions() {
        return listOptions;
    }

    public WebElement getDropdownOptionByPartialName(String optionName) {
        return getListOptions().stream()
                .filter(current -> current.getText().toLowerCase().contains(optionName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new DropdownNotFoundException(
                        String.format(TopPart.OPTION_NOT_FOUND_MESSAGE, optionName, getListOptionsText())));
    }

    public List<String> getListOptionsText() {
        List<String> result = new ArrayList<>();
        for (WebElement current : getListOptions()) {
            result.add(current.getText());
        }
        return result;
    }

    public boolean isExistDropdownOptionByPartialName(String optionName) {
        return getListOptionsText()
                .stream()
                .anyMatch(current -> current.toLowerCase()
                        .contains(optionName.toLowerCase()));
    }

    public void clickDropdownOptionByPartialName(String optionName) {
        getDropdownOptionByPartialName(optionName).click();
    }
}
