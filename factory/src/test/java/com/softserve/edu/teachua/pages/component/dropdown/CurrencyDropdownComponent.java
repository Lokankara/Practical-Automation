package com.softserve.edu.teachua.pages.component.dropdown;

import com.softserve.edu.teachua.exception.DropdownOptionNotFoundException;
import com.softserve.edu.teachua.pages.component.option.CurrencyOptions;

import com.softserve.edu.teachua.pages.component.option.factory.DropdownOption;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CurrencyDropdownComponent extends DropdownComponent<CurrencyDropdownComponent> {

    public CurrencyDropdownComponent(WebDriver driver) {
        super(driver, CurrencyOptions.values());
    }

    @FindBy(css = "#freepay-currencies li a")
    private List<WebElement> dropdownOptions;

    public List<WebElement> getDropdownOptions() {
        return dropdownOptions;
    }

    public WebElement getOptionElement(DropdownOption<CurrencyDropdownComponent> option) {
        return getDropdownOptions().stream()
                .filter(e -> e.getText().equalsIgnoreCase(option.getOptionName()))
                .findFirst()
                .orElseThrow(() -> new DropdownOptionNotFoundException("Option not found: ", option.getOptionName()));
    }

    public void clickUAH() {
        getOptionElement(CurrencyOptions.UAH).click();
    }

    public void clickUSD() {
        getOptionElement(CurrencyOptions.USD).click();
    }

    public void clickEUR() {
        getOptionElement(CurrencyOptions.EUR).click();
    }

    public void select(DropdownOption<CurrencyDropdownComponent> option) {
        factory.selectOption(this, option);
    }
}
