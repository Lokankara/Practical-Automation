package com.softserve.edu.teachua.pages.component.dropdown;

import com.softserve.edu.teachua.pages.component.option.factory.OptionFactory;
import com.softserve.edu.teachua.pages.component.option.factory.OptionHandler;
import com.softserve.edu.teachua.pages.top.BaseModel;
import org.openqa.selenium.WebDriver;

public abstract class DropdownComponent<T> extends BaseModel {
    public DropdownComponent(WebDriver driver, OptionHandler<T>[] options) {
        super(driver);
        this.factory = new OptionFactory<>(options);
    }

    protected OptionFactory<T> factory;
}
