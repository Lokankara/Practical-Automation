package com.softserve.edu.teachua.pages.top;

import com.softserve.edu.teachua.exceptions.DropdownNotFoundException;
import com.softserve.edu.teachua.wraps.search.Search;
import com.softserve.edu.teachua.wraps.search.SearchStrategy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DropdownComponent {

    protected Search search;
    private List<WebElement> listOptions;

    public DropdownComponent(String cssLocator) {
        search = SearchStrategy.getSearch();
        initElements(cssLocator);
    }

    private void initElements(String cssLocator) {
        listOptions = search.cssSelectors(cssLocator);
    }

    public List<WebElement> getListOptions() {
        return listOptions;
    }

    public WebElement getDropdownOptionByPartialName(String optionName) {
        return getListOptions()
                .stream()
                .filter(current -> current.getText().toLowerCase().contains(optionName.toLowerCase()))
                .findFirst().orElseThrow(() -> new DropdownNotFoundException(
                        String.format(TopPart.OPTION_NOT_FOUND_MESSAGE, optionName, getListOptionsText())));
    }

    public List<String> getListOptionsText() {
        return getListOptions().stream().map(WebElement::getText).toList();
    }

    public boolean isExistDropdownOptionByPartialName(String optionName) {
        return getListOptionsText()
                .stream()
                .anyMatch(current -> current.toLowerCase().contains(optionName.toLowerCase()));
    }

    public void clickDropdownOptionByPartialName(String optionName) {
        getDropdownOptionByPartialName(optionName).click();
    }
}
