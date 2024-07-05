package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.tools.ReportUtils;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class DropdownComponent {

    protected Search search;
    private List<WebElement> listOptions;

    public DropdownComponent(By searchLocator) {
        ReportUtils.logInfo("Initializing DropdownComponent with searchLocator: " + searchLocator);
        search = SearchStrategy.getSearch();
        initElements(searchLocator);
    }

    private void initElements(By searchLocator) {
        listOptions = search.webElements(searchLocator);
        ReportUtils.logInfo("Initialized listOptions with searchLocator: " + searchLocator);
    }

    public List<WebElement> getListOptions() {
        ReportUtils.logInfo("Retrieving listOptions");
        return listOptions;
    }

    public WebElement getDropdownOptionByPartialName(String optionName) {
        ReportUtils.logInfo("Searching for dropdown option by partial name: " + optionName);
        WebElement result = getListOptions().stream()
                .filter(current -> current.getText().toLowerCase().contains(optionName.toLowerCase()))
                .findFirst().orElse(null);
        if (result == null) {
            ReportUtils.logAction("OptionName not found: " + optionName);
            throw new RuntimeException(String.format(TopPart.OPTION_NOT_FOUND_MESSAGE, optionName, getListOptionsText()));
        }
        ReportUtils.logInfo("Found dropdown option: " + result.getText());
        return result;
    }

    public List<String> getListOptionsText() {
        ReportUtils.logInfo("Retrieving text of all dropdown options");
        List<String> result = getListOptions().stream().map(WebElement::getText).collect(Collectors.toList());
        ReportUtils.logInfo("Retrieved dropdown options text: " + result);
        return result;
    }

    public boolean isExistDropdownOptionByPartialName(String optionName) {
        ReportUtils.logInfo("Checking existence of dropdown option by partial name: " + optionName);
        boolean isFound = getListOptionsText().stream()
                .anyMatch(current -> current.toLowerCase().contains(optionName.toLowerCase()));
        ReportUtils.logInfo("Existence check result for option name " + optionName + ": " + isFound);
        return isFound;
    }

    public void clickDropdownOptionByPartialName(String optionName) {
        ReportUtils.logInfo("Clicking dropdown option by partial name: " + optionName);
        getDropdownOptionByPartialName(optionName).click();
        ReportUtils.logAction("Clicked dropdown option: " + optionName);
    }
}
