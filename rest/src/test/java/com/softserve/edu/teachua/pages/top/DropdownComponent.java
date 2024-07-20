package com.softserve.edu.teachua.pages.top;

import java.util.ArrayList;
import java.util.List;

import com.softserve.edu.teachua.exception.ComponentNotFoundException;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DropdownComponent {

    protected Search search;
    private List<WebElement> listOptions;

    public DropdownComponent(By searchLocator) {
        search = SearchStrategy.getSearch();
        initElements(searchLocator);
    }

    private void initElements(By searchLocator) {
        search = SearchStrategy.setExplicitExistFirstText();
        search.isLocated(searchLocator);
        search = SearchStrategy.restoreStrategy();
        //
        listOptions = search.webElements(searchLocator);
    }

    // Page Object

    // listOptions
    public List<WebElement> getListOptions() {
        return listOptions;
    }

    // Functional

    public WebElement getDropdownOptionByPartialName(String optionName) {
        WebElement result = null;
        for (WebElement current : getListOptions()) {
            if (current.getText().toLowerCase().contains(optionName.toLowerCase())) {
                result = current;
                break;
            }
        }
        if (result == null) {
            throw new ComponentNotFoundException(String.format(TopPart.OPTION_NOT_FOUND_MESSAGE, optionName, getListOptionsText()));
        }
        return result;
    }

    public List<String> getListOptionsText() {
        List<String> result = new ArrayList<>();
        for (WebElement current : getListOptions()) {
            result.add(current.getText());
        }
        return result;
    }

    public boolean isExistDropdownOptionByPartialName(String optionName) {
        boolean isFound = false;
        for (String current : getListOptionsText()) {
            if (current.toLowerCase().contains(optionName.toLowerCase())) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    public void clickDropdownOptionByPartialName(String optionName) {
        getDropdownOptionByPartialName(optionName).click();
    }

    // Business Logic

}
